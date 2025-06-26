import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {useEffect, useRef} from "react";

const socketUrl = "http://10.10.8.24:8080/ws-seat";
const useSeatWebSocket = (selectSeat) => {
    let stompClient = useRef(null);                                    // biến để người dùng kết nối websocket
    useEffect(() => {
        const socket = new SockJS(socketUrl);   // kết nối socket theo endpoint cài đặt ở BE
        const client = new Client({
                webSocketFactory: () => socket,// cung cấp socket cho người dùng để nó hiểu được kết nối đến đâu
                reconnectDelay: 4000,
                onConnect: () => {
                    console.log("Kết nối websocket thành công")
                    client.subscribe("/topic/select-seat-update",// lắng nghe tin nhắn từ sever trả về khi người dùng chọn ghế
                        (message) => {
                            const data = JSON.parse(message.body);
                            console.log("Dữ liệu người dùng gửi: "+data.seatId)
                            selectSeat(data)// nếu như có dữ liệu thì part rồi truyền vào tham số của hàm
                        });
                }
            }
        );
        client.activate();// kết nối websocket
        stompClient.current = client
        return () => {
            if (stompClient.current) {
                stompClient.current.deactivate();
                console.log("ngắt kết nối websocket")
            }
        }
    }, []);

    const handleSendSelectSeat = (seat) => {
        if (stompClient.current&&stompClient.current.connected){ // connected trả về tru/false xem đã kết nối thành công hay chưa
            stompClient.current.publish({
                destination: "/app/select-seat", // destination là 1 thuộc tính để định tuyến đường dẫn gửi và nhận
                body: JSON.stringify(seat)
            });
        }

    }
    return {handleSendSelectSeat}
}
export default useSeatWebSocket;