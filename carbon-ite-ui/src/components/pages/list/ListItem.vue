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
import ListItem from './ListItem.ts';

export default ListItem
</script>
<style lang="scss">
    @import url(./ListItem.scss);
</style>
