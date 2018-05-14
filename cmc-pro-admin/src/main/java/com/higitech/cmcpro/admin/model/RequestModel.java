package com.higitech.cmcpro.admin.model;

import cn.hutool.core.util.StrUtil;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestModel extends ModelMap {
    private static final long serialVersionUID = -7215626568884548385L;

    private Integer pageindex = 1;
    private Integer pagesize = 10;

    public Object getPagePageHelper() {
        if (super.get("pageindex") != null && StrUtil.isNotEmpty(super.get("pageindex").toString())) {
            this.pageindex = Integer.valueOf(super.get("pageindex").toString());
        }

        if (super.get("pagesize") != null && StrUtil.isNotEmpty(super.get("pagesize").toString())) {
            this.pagesize = Integer.valueOf(super.get("pagesize").toString());
        }

        Map<String, Object> pageMap = new HashMap();
        pageMap.put("pageNum", this.pageindex);
        pageMap.put("pageSize", this.pagesize);
        return pageMap;
    }

    public Integer getPageIndex() {
        if (super.get("pageindex") != null && StrUtil.isNotEmpty(super.get("pageindex").toString())) {
            this.pageindex = Integer.valueOf(super.get("pageindex").toString());
        }
        return this.pageindex;
    }

    public Integer getPageSize() {
        if (super.get("pagesize") != null && StrUtil.isNotEmpty(super.get("pagesize").toString())) {
            this.pagesize = Integer.valueOf(super.get("pagesize").toString());
        }
        return this.pagesize;
    }


    public String getString(String key) {
        return !this.isEmpty() && this.containsKey(key) && this.get(key) != null ? this.get(key).toString() : null;
    }

    public Object getObject(String key) {
        return !this.isEmpty() && this.containsKey(key) && this.get(key) != null ? this.get(key) : null;
    }

    public Float getFloat(String key) {
        if (this != null && !this.isEmpty() && this.containsKey(key) && this.get(key) != null) {
            String val = String.valueOf(this.get(key));
            return val.length() == 0 ? null : Float.parseFloat(val);
        } else {
            return null;
        }
    }

    public Integer getInteger(String key) {
        if (this != null && !this.isEmpty()) {
            if (!this.containsKey(key)) {
                return null;
            } else {
                return this.get(key) == null ? null : Integer.parseInt(this.get(key).toString());
            }
        } else {
            return null;
        }
    }

    public Long getLong(String key) {
        if (this != null && !this.isEmpty()) {
            if (!this.containsKey(key)) {
                return null;
            } else {
                return this.get(key) == null ? null : Long.parseLong(this.get(key).toString());
            }
        } else {
            return null;
        }
    }

    public Map<String, Object> getMap(String key) {
        return !this.isEmpty() && !StrUtil.isEmpty(key) && this.containsKey(key) && this.get(key) != null ? (Map)this.get(key) : null;
    }

    public List<Map<String, Object>> getList(String key) {
        if (this != null && !this.isEmpty() && !StringUtils.isEmpty(key)) {
            if (!this.containsKey(key)) {
                return null;
            } else {
                return this.get(key) == null ? null : (List)this.get(key);
            }
        } else {
            return null;
        }
    }

    public boolean getBoolean(String key) {
        if (this != null && !this.isEmpty() && !StrUtil.isEmpty(key)) {
            if (!this.containsKey(key)) {
                return false;
            } else {
                return this.get(key) == null ? false : (Boolean)this.get(key);
            }
        } else {
            return false;
        }
    }
}
