
/**
 * Represents the information of a given script
 * execution
 * 
 * @since 0.2.0
 */
export default interface Script {

    /** 
     * The identifier of the script
     * 
     * @since 0.2.0
     */
    uuid:String

    /**
     * The name of the script
     * 
     * @since 0.2.0
     */
    name:string

    /**
     * The current status of the script
     * 
     * @since 0.2.0
     */
    status:string
}