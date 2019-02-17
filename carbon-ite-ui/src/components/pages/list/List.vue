<template>
    <ul>
        <li v-for="execution in executions"
            v-bind:key="execution.id">
            <list-item 
                :title="execution.title" 
                :status="execution.status" />
        </li>
    </ul>
</template>
<script lang="ts">
import Vue from 'vue';
import ListItem from './ListItem.vue';

export default Vue.extend({
    name: 'list',
    data: () => {
        return {
            executions: [
                { id: 1, title: 'nasdaq-100', status: 'running' }, 
                { id: 2, title: 'ibex-35', status: 'failure' }
            ]
        }
    },
    components: {
        ListItem
    }
})
</script>
<style lang="postcss" scoped>
    ul {
        list-style: none;

        & li {
            list-style: none;
        }
    }
</style>


