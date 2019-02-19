<template>
    <article class="card">
        <header>
            <h1>{{ title }}</h1>
            <span :class="status"><a>{{ status.toUpperCase() }}</a></span>
            <p>This script sends a daily report over the most important events in the system</p>
        </header>
        <div class="content">  
            <h2>TAGS</h2>
            <ul>
                <li>reports</li>
                <li>events</li>
                <li>system</li>
            </ul>  
        </div>                     
    </article>                    
</template>
<script lang="ts">
import { Component, Vue, Prop } from 'vue-property-decorator';

/**
 * Represents a script entry in the UI
 * 
 * @since 0.2.0
 */
@Component
export default class ListItem extends Vue {

    @Prop() uuid!:string
    @Prop() title!: string
    @Prop() status!: string
}
</script>
<style lang="scss">
    @import url(./ListItem.scss);
</style>
