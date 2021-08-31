<script>
    import SockJS from 'sockjs-client'
    import { Stomp } from '@stomp/stompjs'

    var stompClient = null

    export default {
        name: 'ChatBox',

        data() {
            return {
                message: "",
            }
        },

        methods: {
            sendMessage() {
                const newMessage = JSON.stringify({'content': this.message});
                console.log(newMessage);
                
                const socket = new SockJS('/dashboard');
                stompClient = Stomp.over(socket);
                stompClient.connect({}, frame => {
                    console.log('Connected: ' + frame);
                    stompClient.send('/chat/room/sendMessage', newMessage);
                    stompClient.subscribe('/app/message', function (msg) {
                        console.log("subscribe content: ", msg);
                        this.showMessage(JSON.parse(msg.body).content);
                    });
                    this.message = ""
                })
            },
            
            // for testing only
            showMessage(newMessage) { 
                console.log(newMessage);
            }
        } 
    }
</script>

<style lang="scss" scoped>

$dark-color:  black;
$light-color: white;

input {
    width: 60vw;
    border-radius: 4px;
    margin-right: 10px;
}

input, button {
    border: 2px solid $dark-color;
    background-color: $light-color;
    padding: 12px 20px;
}

button {
    transition-duration: 0.4s;
    border-radius: 2px;;
}

button:hover {
    color: $light-color;
    background-color: $dark-color;
}

</style>

<template>
    <div>
        <form @submit.prevent="sendMessage">
            <input v-model="message" type="text" placeholder="Message...">
            <button @click="sendMessage">Send</button>
        </form>
    </div>

</template>