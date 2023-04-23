package com.zime.ojdemo.untils;

public class Constants {

    /**
     * @Description 提交评测结果的状态码
     * @Since 2021/1/1
     */
    public enum Judge {
        STATUS_NOT_SUBMITTED(-10, "Not Submitted", null),

        STATUS_PENDING(0, "Pending", null),
        STATUS_LINE_UP(1, "Line Up", null),
        STATUS_COMPILING(2, "Compiling", null),
        STATUS_JUDGING(3, "Judging", null),
        STATUS_ACCEPTED(4, "Accepted", "AC"),
        STATUS_PRESENTATION_ERROR(5, "Presentation Error", "PE"),
        STATUS_WRONG_ANSWER(6, "Wrong Answer", "WA"),
        STATUS_TIME_LIMIT_EXCEEDED(7, "Time Limit Exceeded", "TLE"),
        STATUS_MEMORY_LIMIT_EXCEEDED(8, "Memory Limit Exceeded", "MLE"),
        STATUS_OUTPUT_OVERRUN(9, "Output overrun", "OE"),
        STATUS_RUNTIME_ERROR(10, "Runtime Error", "RE"),
        STATUS_COMPILE_ERROR(11, "Compile Error", "CE"),

        STATUS_SYSTEM_ERROR(12, "System Error", "SE"),
        STATUS_PARTIAL_ACCEPTED(13, "Partial Accepted", "PA"),
        STATUS_SUBMITTING(14, "Submitting", null),
        STATUS_SUBMITTED_FAILED(15, "Submitted Failed", null),
        STATUS_NULL(16, "No Status", null);

        private Judge(Integer status, String name, String columnName) {
            this.status = status;
            this.name = name;
            this.columnName = columnName;
        }

        private final Integer status;
        private final String name;
        private final String columnName;

        public Integer getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        public String getColumnName() {
            return columnName;
        }
    }

}
