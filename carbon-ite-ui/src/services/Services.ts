import { http } from './Axios'
import ScriptService from './ScriptService'

export default {
    scripts: new ScriptService(http)
}