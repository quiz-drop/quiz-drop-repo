package com.sparta.quizdemo.common.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketPushHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();

    private Map<String, WebSocketSession> userSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket 연결");
        sessions.add(session);
        log.info(sendPushUsername(session));				//현재 접속한 사람의 username이 출력됨
        String senderId = sendPushUsername(session);
        userSessionMap.put(senderId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("session = " + sendPushUsername(session));
        String msg = message.getPayload();				//js에서 넘어온 메세지
        log.info("msg = " + msg);

        if (!StringUtils.isEmpty(msg)) {
            String[] strs = msg.split(",");

            if (strs != null && strs.length == 3) {
                String pushCategory = strs[0];			/* 채팅, 주문, 배달 구분 */
                String replyWriter = strs[1];			/* 채팅 보낸 유저 */
                String receivePushUser = strs[2];		/* 푸시 알림 받을 유저 */

                WebSocketSession sendedPushSession = userSessionMap.get(receivePushUser);	//로그인상태일때 알람 보냄

                // 채팅
                if ("chat".equals(pushCategory) && sendedPushSession != null) {
                    TextMessage textMsg = new TextMessage(replyWriter + " 님이 " + "<a href='/porfolDetail/' style=\"color:black\"> 채팅을 보냈습니다.</a>");
                    sendedPushSession.sendMessage(textMsg);
                }

                // 주문 완료
                else if ("completedOrder".equals(pushCategory) && sendedPushSession != null) {
                    TextMessage textMsg = new TextMessage(receivePushUser + " 님! " + "<a href='/porfolDetail/' style=\"color:black\"> 주문 완료 되었습니다.</a>");
                    sendedPushSession.sendMessage(textMsg);
                }

                // 배달 완료
                else if ("completedDelivery".equals(pushCategory) && sendedPushSession != null) {
                    TextMessage textMsg = new TextMessage(receivePushUser + " 님! " + "<a href='/porfolDetail/' style=\"color:black\"> 배달이 완료 되었습니다. 이용해 주셔서 감사합니다 :D</a>");
                    sendedPushSession.sendMessage(textMsg);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Socket 연결 해제");
        sessions.remove(session);
        userSessionMap.remove(sendPushUsername(session), session);
    }

    //알람을 보내는 유저(댓글작성, 좋아요 누르는 유저)
    private String sendPushUsername(WebSocketSession session) {
        String loginUsername; /* 웹페이지에 접속한 사용자 */

        if (session.getPrincipal() == null) {
            loginUsername = null;
        } else {
            loginUsername = session.getPrincipal().getName();
        }
        return loginUsername;
    }
}