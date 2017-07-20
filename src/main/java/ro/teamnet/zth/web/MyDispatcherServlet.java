package ro.teamnet.zth.web;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andreea.Puscasu on 7/20/2017.
 */
public class MyDispatcherServlet extends HttpServlet {
    Map<String, MethodAttributes> allowedMethods = new HashMap<>();

    public void init() throws ServletException{

        try {
            Controllers(AnnotationScanUtils.getClasses("ro.teamnet.zth.appl.controller"));


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void Controllers(Iterable<Class> classes) {
        for(Class cl : classes){
            MyController myController = (MyController)cl.getDeclaredAnnotations(MyController.class);
            String urlController = myController.urlPath();
            Method methods[] = cl.getDeclaredMethods();
            for(Method method : methods) {
                MyRequestMethod myRequestMethod = (MyRequestMethod) method.getDeclaredAnnotation(MyRequestMethod.class);
                String urlMethod =  "/app/mvc" + urlController +"" + myRequestMethod.urlPath();
                urlMethod += myRequestMethod.methodType();
                MethodAttributes methodAttributes = new MethodAttributes();
                methodAttributes.setControllerClass(myController.getClass().getSimpleName());
                methodAttributes.setMethodName(myRequestMethod.getClass().getSimpleName());
                methodAttributes.setMethodType(myRequestMethod.getClass().getTypeName());
                allowedMethods.put(urlMethod, methodAttributes);
            }
        }
    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "POST");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "GET");
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "PUT");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchReply(request, response, "DELETE");
    }

    private void dispatchReply(HttpServletRequest request, HttpServletResponse response, String post) {
        try {
            Object resultToDisplay = dispatch(request, methodType);
            reply(response, resultToDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendExceptionError(Exception e, HttpServletResponse response) {
    }

    private void reply(HttpServletResponse response, Object resultToDisplay) {

    }

    private Object dispatch(HttpServletRequest request,String methodType)throws Exception{

    }
}
