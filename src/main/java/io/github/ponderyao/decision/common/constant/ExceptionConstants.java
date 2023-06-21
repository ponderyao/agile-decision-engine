package io.github.ponderyao.decision.common.constant;

/**
 * ExceptionConstants: 异常常量
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ExceptionConstants {
    
    public static class HEADER {
        public static final String ENGINE_DECISION = "Decision engine executing failed: ";
        public static final String ENGINE_SCRIPT = "Script engine executing script '%s' failed: ";
        public static final String MANAGE_PERSISTENCE = "Data persistence management exception: ";
        private HEADER() {}
    }
    
    public static class DECISION_DOMAIN {
        public static final String MISSING = "the code of decision domain is not exist";
        public static final String NOT_EXIST = "there is no decision domain coded as '%s'";
        public static final String REPEAT = "failed to register domain, the domain coded as '%s' already exists";
        private DECISION_DOMAIN() {}
    }
    
    public static class DECISION_RULE {
        public static final String EMPTY = "there is no rule in decision domain '%s'";
        public static final String TOO_MUCH = "there is more than one matching rule after the decision. Please ensure that " 
            + "there is only one correct rule for a set of condition [%s]";
        public static final String REPEAT = "failed to define rule, the rule named '%s' already exists in domain '%s'";
        private DECISION_RULE() {}
    }
    
    public static class CONDITION_STUB {
        public static final String EMPTY = "there is no condition stub in decision domain '%s'";
        public static final String VALUE_TYPE = "the condition value type '%s' is unsupported";
        public static final String CONVERT = "convert condition value '%s' to type '%s': %s";
        public static final String REPEAT = "failed to define condition stub, the condition with code '%s' already exists in domain '%s'";
        private CONDITION_STUB() {}
    }
    
    public static class CONDITION_ENTRY {
        public static final String EMPTY = "there is no condition entry in decision domain '%s'. Please ensure that" 
            + "there is at lease one condition entry when decision-making";
        public static final String REPEAT = "failed to realize condition '%s', the condition entry of rule '%s' already exists in domain '%s'";
        private CONDITION_ENTRY() {}
    }
    
    public static class ACTION_STUB {
        public static final String REPEAT = "failed to define action stub, the action with code '%s' already exists in domain '%s'";
        private ACTION_STUB() {}
    }
    
    public static class ACTION_ENTRY {
        public static final String EMPTY = "there is no action entry for rule '%s' in decision domain '%s'. Please ensure that"
            + "there is at lease one action entry when decision-making";
        public static final String LOOP = "the next-action chain of action entry shouldn't exists loop";
        private ACTION_ENTRY() {}
    }
    
    public static class SCRIPT_CONDITION {
        public static final String RESULT_EMPTY = "the script execution result of condition '%s' cannot be empty";
        private SCRIPT_CONDITION() {}
    }
    
    public static class SCRIPT {
        public static final String INSTANTIATION = "groovy script instantiation fail: %s";
        public static final String METHOD = "the main method '%s' of groovy script is never defined";
        public static final String EXECUTION = "groovy script executing error: %s";
        private SCRIPT() {}
    }
    
}
