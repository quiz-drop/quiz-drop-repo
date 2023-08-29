package com.sparta.quizdemo.order.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.quizdemo.cart.entity.Cart;
import com.sparta.quizdemo.cart.entity.CartItem;
import com.sparta.quizdemo.cart.repository.CartItemRepository;
import com.sparta.quizdemo.cart.repository.CartRepository;
import com.sparta.quizdemo.cart.service.CartServiceImpl;
import com.sparta.quizdemo.common.dto.ApiResponseDto;
import com.sparta.quizdemo.common.security.UserDetailsImpl;
import com.sparta.quizdemo.option.entity.Option;
import com.sparta.quizdemo.user.entity.User;
import com.sparta.quizdemo.common.entity.UserRoleEnum;
import com.sparta.quizdemo.order.dto.OrderRequestDto;
import com.sparta.quizdemo.order.dto.OrderResponseDto;
import com.sparta.quizdemo.order.entity.Order;
import com.sparta.quizdemo.order.entity.OrderItem;
import com.sparta.quizdemo.order.repository.OrderItemRepository;
import com.sparta.quizdemo.order.repository.OrderRepository;
import com.sparta.quizdemo.sse.entity.NotificationType;
import com.sparta.quizdemo.sse.service.NotificationService;
import com.sparta.quizdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartServiceImpl cartService;
    private final NotificationService notificationService;

    public ResponseEntity<ApiResponseDto> createOrder(OrderRequestDto orderRequestDto, User user) {
        if (cartRepository.findByUserId(user.getId()).isEmpty()) {
            cartService.createCart(user);
        }

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new NullPointerException("장바구니가 없습니다."));
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(cart.getId());

        long totalPrice = 0L;
        long totalCookingTime = 0L;
        long optionPrice = 0L;

        if (cartItemList != null) {
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getOptionList().isEmpty()) {
                    totalPrice += (cartItem.getProduct().getProductPrice() * cartItem.getQuantity());
                    totalCookingTime += (cartItem.getProduct().getCookingTime()) * cartItem.getQuantity();
                } else {
                    for (Option option : cartItem.getOptionList()) {
                        optionPrice += option.getOptionPrice();
                    }
                    totalPrice += ((cartItem.getProduct().getProductPrice() + optionPrice) * cartItem.getQuantity());
                    totalCookingTime += (cartItem.getProduct().getCookingTime()) * cartItem.getQuantity();
                }
            }
        }

        // 현재 시간
        LocalDateTime localDateTime = LocalDateTime.now();
        // 현재 시간에 총 조리시간 더하기
        LocalDateTime completeTime = localDateTime.plusMinutes(totalCookingTime);

        if (orderRequestDto.getDelivery()) {
            String userAddress = getKakaoApiFromAddress(user.getAddress().getAddress1() + " " + user.getAddress().getAddress2());
            HashMap<String, String> xyMap = getXYMapfromJson(userAddress);

            double lat2 = 0;
            double lon2 = 0;
            String city = "";
            Long distance = 0L;

            lat2 = Double.parseDouble(xyMap.get("y")); // 위도
            lon2 = Double.parseDouble(xyMap.get("x")); // 경도
            city = xyMap.get("z").substring(1);
            log.info(xyMap.get("z"));

            switch (city) {
                case "서울": distance = distance(37.330689, 126.593066, lat2, lon2);
                    break;
                case "경기": distance = distance(37.569148, 127.243353, lat2, lon2);
                    break;
                case "강원": distance = distance(37.746002, 128.383841, lat2, lon2);
                    break;
                case "충북": distance = distance(36.891643, 127.815570, lat2, lon2);
                    break;
                case "충남": distance = distance(36.583290, 126.866889, lat2, lon2);
                    break;
                case "전북": distance = distance(35.853520, 127.121177, lat2, lon2);
                    break;
                case "전남": distance = distance(35.080598, 126.885858, lat2, lon2);
                    break;
                case "경북": distance = distance(36.460540, 128.915577, lat2, lon2);
                    break;
                case "경남": distance = distance(35.394316, 128.085717, lat2, lon2);
                    break;
                case "제주": distance = distance(35.394316, 128.085717, lat2, lon2);
                    break;
                default: distance = distance(37.27538, 127.05488, lat2, lon2);
            }

            totalPrice += 2000L;
            completeTime = completeTime.plusMinutes(distance);
        }

        if (orderRequestDto.getPayment().equals(totalPrice)) {
            // 현재 유저의 order 생성
            Order order = new Order(user, totalPrice, completeTime, orderRequestDto.getDelivery(), orderRequestDto.getRequest(), orderRequestDto.getOrderComplete());
            orderRepository.save(order);

            List<Order> userOrderList = orderRepository.findAllByUserIdOrderByCreatedAtAsc(user.getId());
            if (userOrderList.size() > 10) {
                orderRepository.delete(userOrderList.remove(0));
            }

            if (cartItemList != null) {
                for (CartItem cartItem : cartItemList) {
                    OrderItem orderItem = new OrderItem(cartItem, order, cartItem.getOptionList());
                    orderItemRepository.save(orderItem);
                    cartItemRepository.delete(cartItem);
                }
            }

            String url = "";
            String orderUsername = order.getUser().getUsername();
            Optional<User> orderUser = orderRepository.findByUsername(order.getUser().getUsername());
            if (orderUser.isPresent()) {
                User receiver = orderUser.get();
                String content = receiver + "님! 주문이 완료되었습니다!";
                notificationService.send(receiver, NotificationType.ORDER, content, url);
            } else {
                log.error("User not found");
                throw new IllegalArgumentException("존재하지 않는 유저입니다.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto("결제가 완료 되었습니다.", HttpStatus.CREATED.value()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("금액을 올바르게 입력해 주세요.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    public ResponseEntity<List<OrderResponseDto>> getDoneOrderList() {
        List<Order> orderList = orderRepository.findAllByOrderByCreatedAtDesc();
        if (orderList.isEmpty()) {
            throw new NullPointerException("주문이 없습니다.");
        } else {
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            for (Order order : orderList) {
                if (order.getOrderComplete()) {
                    OrderResponseDto orderResponseDto = new OrderResponseDto(order);
                    orderResponseDtoList.add(orderResponseDto);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
        }
    }

    public ResponseEntity<List<OrderResponseDto>> getReadyOrderList() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw new NullPointerException("주문이 없습니다.");
        } else {
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            for (Order order : orderList) {
                if (!order.getOrderComplete()) {
                    OrderResponseDto orderResponseDto = new OrderResponseDto(order);
                    orderResponseDtoList.add(orderResponseDto);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
        }
    }

    public ResponseEntity<List<OrderResponseDto>> getMyOrders(User user) {
        List<Order> orderList = orderRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
        if (orderList.isEmpty()) {
            throw new NullPointerException("주문이 없습니다.");
        } else {
            List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
            for (Order order : orderList) {
                OrderResponseDto orderResponseDto = new OrderResponseDto(order);
                orderResponseDtoList.add(orderResponseDto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(orderResponseDtoList);
        }
    }

    public ResponseEntity<OrderResponseDto> getOneOrder(Long orderNo, User user) {
        Order order = orderRepository.findById(orderNo).orElseThrow(() -> new NullPointerException("존재하지 않는 주문 번호입니다."));
        if (user.getId().equals(order.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            return ResponseEntity.status(HttpStatus.OK).body(new OrderResponseDto(order));
        } else {
            throw new IllegalArgumentException("해당 번호의 주문에 대한 권한이 없습니다.");
        }
    }

    public ResponseEntity<ApiResponseDto> cancelOrder(Long orderNo, User user) {
        Order order = orderRepository.findById(orderNo).orElseThrow(() -> new NullPointerException("존재하지 않는 주문 번호입니다."));
        if (user.getId().equals(order.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (order.getOrderComplete()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("이미 완료된 주문입니다.", HttpStatus.BAD_REQUEST.value()));
            } else {
                orderRepository.delete(order);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("주문이 취소 되었습니다.", HttpStatus.OK.value()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto("해당 주문에 대한 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    public void completeOrder() {
        List<Order> orderList = orderRepository.findAll();
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                LocalDateTime localDateTime = LocalDateTime.now();
                if (order.getCompleteTime().isBefore(localDateTime) && order.getOrderComplete().equals(false)) {
                    for (OrderItem orderItem : order.getOrderItemList()) {
                        Long tempProductOrderCount = orderItem.getProduct().getOrderCount();
                        tempProductOrderCount = tempProductOrderCount + orderItem.getQuantity();
                        orderItem.getProduct().setOrderCount(tempProductOrderCount);
                    }
                    Long tempUserOrderCount = order.getUser().getOrderCount();
                    order.getUser().setOrderCount(tempUserOrderCount + 1);
                    order.setOrderComplete(true);

                    List<Order> totalOrderList = orderRepository.findAllByOrderByCreatedAtAsc();
                    List<Order> completedOrderList = new ArrayList<>();
                    for (Order order2 : totalOrderList) {
                        if (order2.getOrderComplete()) {
                            completedOrderList.add(order2);
                            if (completedOrderList.size() > 100) {
                                orderRepository.delete(completedOrderList.remove(0));
                            }
                        }
                    }

                    String url = "";
                    String orderUsername = order.getUser().getUsername();
                    Optional<User> orderUser = orderRepository.findByUsername(order.getUser().getUsername());
                    if (orderUser.isPresent()) {
                        User receiver = orderUser.get();
                        String content = receiver + "님! 배달이 완료되었습니다!";
                        notificationService.send(receiver, NotificationType.DELIVERY, content, url);
                    } else {
                        log.error("User not found");
                        throw new IllegalArgumentException("존재하지 않는 유저입니다.");
                    }
                }
            }
        }
    }

    public String getKakaoApiFromAddress(String roadFullAddr) {
        String apiKey = "cd1ccf91994a50633a55d68e5f85d9a2";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String jsonString = null;

        try {
            roadFullAddr = URLEncoder.encode(roadFullAddr, "UTF-8");

            String addr = apiUrl + "?query=" + roadFullAddr;

            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);

            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();

            String line;

            while ((line=rd.readLine()) != null) {
                docJson.append(line);
            }

            jsonString = docJson.toString();
            rd.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public HashMap<String, String> getXYMapfromJson(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> XYMap = new HashMap<String, String>();

        try {
            com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>> typeRef
                    = new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>(){};
            Map<String, Object> jsonMap = mapper.readValue(jsonString, typeRef);

            @SuppressWarnings("unchecked")
            List<Map<String, String>> docList
                    =  (List<Map<String, String>>) jsonMap.get("documents");

            Map<String, String> adList = (Map<String, String>) docList.get(0);
            XYMap.put("x",adList.get("x"));
            XYMap.put("y", adList.get("y"));
            XYMap.put("z", adList.get("address_name"));

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XYMap;
    }

    // 두 지점 간의 거리를 킬로미터 단위로 구하는 메서드
    private static Long distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (long)dist;
    }
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
