<script>

    import { GOOGLE_AUTH_URL } from "../oauthConfig"

    export default {
        name: 'Login',

        data(){
            return {
                googleAuthUrl: GOOGLE_AUTH_URL
            }
        },
    }
    
</script>

<template>
    <!-- TODO: center `v-container` both horizontally & vertically -->
    <v-container fluid>
        <h1>Welcome to Chatty!</h1>
        <v-btn color="error" fab :href="googleAuthUrl">
            Log in with
            <v-icon small right>mdi-google</v-icon>
        </v-btn>
    </v-container>

</template>
