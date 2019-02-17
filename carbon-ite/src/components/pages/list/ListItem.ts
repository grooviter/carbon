import { Component, Vue, Prop } from 'vue-property-decorator';

@Component
export default class ListItem extends Vue {

    @Prop() title!: string;
    @Prop() status!: string;
}