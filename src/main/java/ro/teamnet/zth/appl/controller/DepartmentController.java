package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Andreea.Puscasu on 7/20/2017.
 */
@MyController(urlPath = "/departaments")
    public class DepartmentController {
    @MyRequestMethod(urlPath = "all",methodType = "GET")
    private String getAllDepartments(){
        return "allDepartments";
    }
}
