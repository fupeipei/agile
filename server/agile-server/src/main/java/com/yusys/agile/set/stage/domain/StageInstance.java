package com.yusys.agile.set.stage.domain;

import java.io.Serializable;
import java.util.List;

public class StageInstance extends KanbanStageInstance implements Serializable {

    private static final long serialVersionUID = -5161686078078730079L;

    private List<KanbanStageInstance> secondStages;

    public List<KanbanStageInstance> getSecondStages() {
        return secondStages;
    }

    public void setSecondStages(List<KanbanStageInstance> secondStages) {
        this.secondStages = secondStages;
    }

    @Override
    public String toString() {
        return "StageInstance{" +
                "secondStages=" + secondStages +
                '}';
    }
}
