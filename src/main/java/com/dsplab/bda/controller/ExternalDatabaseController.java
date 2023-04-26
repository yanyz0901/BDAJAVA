package com.dsplab.bda.controller;

import com.dsplab.bda.domain.ResponseResult;
import com.dsplab.bda.service.ExternalDatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "访问数据库api")
@RequestMapping("/database")
public class ExternalDatabaseController {

    @Autowired
    private ExternalDatabaseService externalDatabaseService;

    @GetMapping("kegg/getList")
    @ApiOperation(value = "查询KEGG数据库列表", notes = "不需要携带token，不需要传参数")
    public ResponseResult getKEGGList(){
        return externalDatabaseService.getKEGGList();
    }

    @GetMapping("bigg/getList")
    @ApiOperation(value = "查询BiGG数据库列表", notes = "不需要携带token，不需要传参数")
    public ResponseResult getBiGGList(){
        return externalDatabaseService.getBiGGList();
    }

    @GetMapping("kegg/getInfoById/{keggId}")
    @ApiOperation(value = "根据id查询KEGG数据库", notes = "不需要携带token，需要传参数keggId，例如C00002")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keggId", value = "待查询的id，例如C00002"),
    })
    public ResponseResult getKEGGInfoById(@PathVariable String keggId){
        return externalDatabaseService.getKEGGInfoById(keggId);
    }

    @GetMapping("bigg/getInfoById/{biggModelId}")
    @ApiOperation(value = "根据id查询BiGG数据库", notes = "不需要携带token，需要传参数biggModelId，例如iND750")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "biggModelId", value = "待查询的id，例如iND750"),
    })
    public ResponseResult getBiGGInfoById(@PathVariable String biggModelId){
        return externalDatabaseService.getBiGGInfoById(biggModelId);
    }

    @GetMapping("biocyc/getInfoById/{biocycId}")
    @ApiOperation(value = "根据id查询BioCyc数据库", notes = "不需要携带token，需要传参数biocycId，例如biocyc14-15682-3673724563")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "biocycId", value = "待查询的id，例如biocyc14-15682-3673724563"),
    })
    public ResponseResult getBioCycInfoById(@PathVariable String biocycId){
        return externalDatabaseService.getBioCycInfoById(biocycId);
    }
}
