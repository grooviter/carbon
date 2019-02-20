/**
 * Represents a Script execution information
 *
 * @since 0.2.0
 */
export interface Script {
    uuid: string;
    name: string;
    status: string;
}

/**
 * Represents the script related state
 *
 * @since 0.2.0
 */
export interface ScriptState {
    list: Script[];
}
