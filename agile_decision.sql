-- --------------------------
-- decision_domain 决策域
-- --------------------------
DROP TABLE IF EXISTS decision_domain;
CREATE TABLE decision_domain (
    `domain_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '决策域标识',
    `domain_code` VARCHAR(512) NOT NULL COMMENT '决策域编码',
    `domain_name` VARCHAR(512) NOT NULL COMMENT '决策域名称',
    `domain_desc` VARCHAR(1024) DEFAULT NULL COMMENT '决策域说明',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`domain_id`),
    UNIQUE INDEX idx_decision_domain_domain_code(`domain_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='决策域表';


-- ---------------------------
-- condition_stub 条件桩
-- ---------------------------
DROP TABLE IF EXISTS condition_stub;
CREATE TABLE condition_stub (
    `condition_stub_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '条件桩标识',
    `domain_id` BIGINT(16) NOT NULL COMMENT '决策域标识',
    `condition_code` VARCHAR(512) NOT NULL COMMENT '条件编码',
    `condition_name` VARCHAR(512) NOT NULL COMMENT '条件名称',
    `condition_type` VARCHAR(512) NOT NULL COMMENT '条件类型',
    `condition_desc` VARCHAR(1024) DEFAULT NULL COMMENT '条件说明',
    `condition_script` LONGTEXT DEFAULT NULL COMMENT '条件脚本',
    `script_method` VARCHAR(128) DEFAULT NULL COMMENT '脚本方法',
    `prev_condition` VARCHAR(512) DEFAULT NULL COMMENT '前置条件，按逗号分割',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`condition_stub_id`),
    INDEX idx_condition_stub_domain_id(`domain_id`),
    INDEX idx_condition_stub_condition_code(`condition_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='条件桩表';


-- ---------------------------
-- action_stub 动作桩
-- ---------------------------
DROP TABLE IF EXISTS action_stub;
CREATE TABLE action_stub (
    `action_stub_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '动作桩标识',
    `domain_id` BIGINT(16) NOT NULL COMMENT '决策域标识',
    `action_code` VARCHAR(512) NOT NULL COMMENT '动作编码',
    `action_name` VARCHAR(512) NOT NULL COMMENT '动作名称',
    `action_desc` VARCHAR(1024) DEFAULT NULL COMMENT '动作说明',
    `action_script` LONGTEXT DEFAULT NULL COMMENT '动作脚本',
    `script_method` VARCHAR(128) DEFAULT NULL COMMENT '脚本方法',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`action_stub_id`),
    INDEX idx_action_stub_domain_id(`domain_id`),
    INDEX idx_action_stub_action_code(`action_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='动作桩表';


-- ----------------------------
-- decision_rules 决策规则
-- ----------------------------
DROP TABLE IF EXISTS decision_rule;
CREATE TABLE decision_rule (
    `rule_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '决策规则标识',
    `domain_id` BIGINT(16) NOT NULL COMMENT '决策域标识',
    `rule_name` VARCHAR(512) NOT NULL COMMENT '决策规则名称',
    `rule_desc` VARCHAR(1024) DEFAULT NULL COMMENT '决策规则说明',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`rule_id`),
    INDEX idx_decision_rule_domain_id(`domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='决策规则表';


-- -----------------------------
-- condition_entry 条件项
-- -----------------------------
DROP TABLE IF EXISTS condition_entry;
CREATE TABLE condition_entry (
    `condition_entry_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '条件项标识',
    `domain_id` BIGINT(16) NOT NULL COMMENT '决策域标识',
    `rule_id` BIGINT(16) NOT NULL COMMENT '决策规则标识',
    `condition_stub_id` BIGINT(16) NOT NULL COMMENT '条件桩标识',
    `condition_value` VARCHAR(512) DEFAULT NULL COMMENT '条件值',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`condition_entry_id`),
    INDEX idx_condition_entry_domain_id(`domain_id`),
    INDEX idx_condition_entry_rule_id(`rule_id`),
    INDEX idx_condition_entry_condition_stub_id(`condition_stub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='条件项表';


-- -----------------------------
-- action_entry 动作项
-- -----------------------------
DROP TABLE IF EXISTS action_entry;
CREATE TABLE action_entry (
    `action_entry_id` BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '动作项标识',
    `domain_id` BIGINT(16) NOT NULL COMMNET '决策域标识',
    `rule_id` BIGINT(16) NOT NULL COMMENT '决策规则标识',
    `action_stub_id` BIGINT(16) NOT NULL COMMENT '动作桩标识',
    `next_action` VARCHAR(512) DEFAULT NULL COMMENT '后置动作，按逗号分割',
    `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT now() ON UPDATE now() COMMENT '修改时间',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '生效状态',
    PRIMARY KEY (`action_entry_id`),
    INDEX idx_action_entry_domain_id(`domain_id`),
    INDEX idx_action_entry_rule_id(`rule_id`),
    INDEX idx_action_entry_action_stub_id(`action_stub_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='动作项表';