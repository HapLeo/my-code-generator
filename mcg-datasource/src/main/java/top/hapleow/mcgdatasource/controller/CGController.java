package top.hapleow.mcgdatasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.hapleow.mcgdatasource.dto.CodeGenCmd;
import top.hapleow.mcgdatasource.service.ICodeGenService;

import java.util.List;
import java.util.Map;

/**
 * @author wuyulin
 * @description
 * @date 2022/12/2
 */
@RestController
public class CGController {


    @Autowired
    private ICodeGenService codeGenService;


    @PostMapping("/codeGen")
    public String codeGen(@RequestBody CodeGenCmd codeGenCmd) {

        codeGenService.codGen(codeGenCmd);
        return "SUCCESS";
    }


    @GetMapping("/listTables")
    public List<Map<String, Object>> listTables() {
        List<Map<String, Object>> maps = codeGenService.listTables();
        return maps;
    }

    @GetMapping("/listTemplates")
    public List<String> listTemplates() {
        return codeGenService.listTemplates();
    }

}
