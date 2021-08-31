<script>
    import axios from 'axios'

    export default {
        name: 'MessangeList',

        data () {
            return {
                messages: [],
            }
        },

        created () {
            axios.get('https://')
                .then(response => {
                    this.messages = response.data
                    // console.log(this.messages)
                }).catch(error => {
                    console.log('Error: ' + error)
                })
        }
    }
</script>

<style scoped>

</style>

<template>
    <div>
        
    </div>
</template>