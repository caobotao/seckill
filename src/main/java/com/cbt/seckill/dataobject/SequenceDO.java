package com.cbt.seckill.dataobject;

import javax.persistence.*;

@Table(name = "sequence_info")
public class SequenceDO {
    /**
     * 名称
     */
    @Id
    private String name;

    /**
     * 当前值
     */
    @Column(name = "current_value")
    private Integer currentValue;

    /**
     * 步长
     */
    private Integer step;

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取当前值
     *
     * @return current_value - 当前值
     */
    public Integer getCurrentValue() {
        return currentValue;
    }

    /**
     * 设置当前值
     *
     * @param currentValue 当前值
     */
    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 获取步长
     *
     * @return step - 步长
     */
    public Integer getStep() {
        return step;
    }

    /**
     * 设置步长
     *
     * @param step 步长
     */
    public void setStep(Integer step) {
        this.step = step;
    }
}