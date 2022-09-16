package com.huawei.datashow.controller;

import com.huawei.datashow.service.ConnectionPoolService;
import com.huawei.datashow.bean.ConnectionPoolDTOBean;
import com.huawei.datashow.util.MyException;
import com.huawei.datashow.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import javax.sql.DataSource;

@RestController
@RequestMapping("/updateDataSource")
public class ConnectionPoolController
{
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionPoolService connectionPoolService;

    @GetMapping("/now")
    public String now()
    {
        return connectionPoolService.getConnectionPoolsNow();
    }

    @PostMapping("/addHikariCP")
    public Result<Object> addHikariCP(@Validated @RequestBody ConnectionPoolDTOBean dto)
    {
        try {
            connectionPoolService.addHikariCP(dto);
            return Result.OK();
        } catch (MyException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/removeHikariCP")
    public String removeHikariCP(@RequestParam("name") String name)
    {
        return connectionPoolService.removeHikariCP(name);
    }
}
