package carbon.ast

import groovy.transform.Immutable

@Immutable
class TaskArgument {
    String name
    Boolean required
}
