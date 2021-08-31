<script>
    import axios from 'axios'

    export default {
        name: 'RoomList',

        data () {
            return {
                rooms: [],
            }
        },

        created () {
            axios.get('https://')
                .then(response => {
                    this.rooms = response.data
                    // console.log(this.rooms)
                }).catch(error => {
                    console.log('Error: ' + error)
                })
        }
    }
</script>

<style lang="scss" scoped>

</style>

<template>
    <v-col cols="3">
        <v-list>
            
        </v-list>
    </v-col>
</template>