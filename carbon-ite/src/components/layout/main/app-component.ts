import { Component, Vue, Prop, Provide } from 'vue-property-decorator'

@Component
export default class App extends Vue {

    @Provide()
    foo:string = 'foo';

    @Provide()
    bar:string = 'bar';
}
