import com.astav.jsontojava.Generator;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

public class JsonPojoConverter {
    private String className;

    public JsonPojoConverter(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    private String packageName;

    public void makeJsonObject(String json) throws Exception {
        File file = new File("output");

        //convert the file to URL format
        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};
        synchronized (this) {
            ClassLoader defaultClassLoader = Thread.currentThread().getContextClassLoader();

            //load this folder into Class loader
            ClassLoader cl = new ChildURLClassLoader(urls);

            Thread.currentThread().setContextClassLoader(cl);
            Class cls = cl.loadClass(packageName + "." + className);
            Object o = new Gson().fromJson(json, cls);

            Method method = cls.getClass().getMethod("toString", null);
            System.out.println(method.invoke(cls, null));

            Thread.currentThread().setContextClassLoader(defaultClassLoader);
        }
    }

    public void generatePojo(String jsonString) throws IOException, ClassNotFoundException {
        Generator generator = new Generator("output", packageName, null, null);
        generator.generateClasses(className, jsonString);
    }

}

