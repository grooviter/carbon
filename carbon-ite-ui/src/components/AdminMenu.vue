<template>
    <nav class="admin">
        <div class="left">
            <a class="collapse" href="#collapse">
                <font-awesome-icon icon="compress-arrows-alt" size="lg"/>
            </a>
        </div>
        <div class="right">
            <a href="#notifications"><font-awesome-icon icon="bell" size="lg"/></a>
            <a href="#notifications"><font-awesome-icon icon="comments" size="lg"/></a>
            <a class="user" href="#notifications">
                <img src="@/assets/pro4.jpg" />
                <span>Mario Garcia</span>
                <font-awesome-icon icon="caret-down" size="lg" />
            </a>
        </div>
    </nav>
</template>
<script lang="ts">
import Vue from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faBell,
    faComments,
    faCompressArrowsAlt,
    faCaretDown,
} from '@fortawesome/free-solid-svg-icons';

library.add(faBell, faComments, faCompressArrowsAlt, faCaretDown);
export default Vue.extend({
    components: {
        FontAwesomeIcon,
    },
});
</script>
