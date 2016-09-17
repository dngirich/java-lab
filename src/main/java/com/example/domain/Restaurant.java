package com.example.domain;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty
    @Length(max = 10)
    private String name;
    
    @NotNull
    private WorkTime workTime;
    
    @NotNull
    private List<String> halls;
            
    public Restaurant() {
    }

    public Restaurant(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public List<String> getHalls() {
        return halls;
    }

    public void setHalls(List<String> halls) {
        this.halls = halls;
    }
    
    public static class WorkTime {
        
        private String start;
        
        private String end;

        public WorkTime() {
        }

        public WorkTime(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }                
    }
}
