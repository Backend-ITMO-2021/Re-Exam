<template>
    <v-container fluid>
        <v-row>
            <v-col offset-lg="1"><v-btn>New room</v-btn></v-col>
            <v-col offset-lg="2" cols="2"><v-btn>New username</v-btn></v-col>
            <v-col offset-lg="2" cols="2"><v-btn>New room name</v-btn></v-col>
            <v-col offset-lg="2" cols="2"><v-btn>Add a participant</v-btn></v-col>
        </v-row>
    </v-container>
</template>

<script>

export default {
  name: 'Toolbar',
}
</script>
