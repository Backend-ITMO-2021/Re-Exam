<script>

    import Toolbar from '../components/Toolbar.vue'
    import RoomList from '../components/RoomList.vue'
    import ChatWindow from '../components/ChatWindow.vue'

    export default {
        name: 'Dashboard',
        
        components: {
            Toolbar,
            RoomList,
            ChatWindow
        },

        methods: {
            
        }
    }

</script>

<style scoped>

</style>

<template>
    
    <v-container fluid>
        <!-- toolbar area -->
        <v-row cols="12">
            <toolbar/>
        </v-row>
        
        <v-divider></v-divider>
        
        <!-- room list & chat window areas -->
        <v-row>
            <v-col cols="3">
                <room-list/>
            </v-col>

            <v-divider vertical></v-divider>

            <v-col cols="9">
                <chat-window/>
            </v-col>
        </v-row>
    </v-container>

</template>