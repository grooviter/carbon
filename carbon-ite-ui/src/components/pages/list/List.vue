<template>
    <ul>
        <li v-for="execution in executions"
            v-bind:key="execution.uuid">
            <list-item 
                :title="execution.name" 
                :status="execution.status" />
        </li>
    </ul>
</template>
<script lang="ts">
import ListItem from './ListItem.vue';
import Script from '../../../domains/Script'
import api from '../../../services/Services'
import { Component, Vue } from 'vue-property-decorator';

/**
 * Represents a list of current running scripts
 * 
 * @since 0.2.0
 */
@Component({
    components: {
        ListItem
    }
})
export default class List extends Vue {

    /**
     * Current script executions
     * 
     * @since 0.2.0
     */
    executions!:Array<Script>

    data () {
        return {
            executions: []
        }
    }

    mounted () {
        api.scripts.list().then(data => {
            this.executions = data;
        })
    }
}
</script>
<style lang="postcss" scoped>
    ul {
        list-style: none;

        & li {
            list-style: none;
        }
    }
</style>


