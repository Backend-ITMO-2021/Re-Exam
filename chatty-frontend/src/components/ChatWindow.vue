<script>

    import MessageList from '../components/MessageList.vue'
    import ChatBox from '../components/ChatBox.vue'
    // import NewRoom from '../components/NewRoom.vue'
    // import NewRoomName from '../components/NewRoomName.vue'
    // import NewMember from '../components/NewMember.vue'
    // import NewUsername from '../components/NewUsername.vue'

    export default {

        name: 'ChatWindow',

        components: { 
            MessageList, 
            ChatBox, 
            // NewRoom,
            // NewRoomName,
            // NewMember,
            // NewUsername
        }
    }

</script>

<style scoped>

</style>

<template>
    <v-container grid-list-md>

        <v-row cols="12">
            <message-list/>
        </v-row>

        <!-- TODO: replace the `chat box` component by the `new room` component 
                    or pop up a window for this component? -->
        <v-row cols="12">
            <chat-box/>
        </v-row>

    </v-container>
</template>
