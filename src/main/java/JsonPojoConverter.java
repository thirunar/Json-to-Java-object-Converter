import com.astav.jsontojava.Generator;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.UUID;

public class JsonPojoConverter {
    private String className;

    private String packageName;

    public JsonPojoConverter(String packageName, String className) {
        this.className = className;
        this.packageName = packageName;// + this.uuidString;
    }

    public void makeJsonObject(String generatedPackageName, String json, int pid) throws Exception {
        File file = new File("output/");

        //convert the file to URL format
        URL url = file.toURI().toURL();
        URL[] urls = new URL[]{url};

        System.out.println("Entering Sync block.." + pid);
        ClassLoader defaultClassLoader = Thread.currentThread().getContextClassLoader();

        //load this folder into Class loader
        ClassLoader cl = new ChildURLClassLoader(urls);

        Thread.currentThread().setContextClassLoader(cl);
        Class cls = cl.loadClass(generatedPackageName + "." + className);
        Object o = new Gson().fromJson(json, cls);

        Method method = cls.getClass().getMethod("toString", null);
        System.out.println(method.invoke(cls, null));

        Thread.currentThread().setContextClassLoader(defaultClassLoader);
        System.out.println("Exiting Sync block.." + pid);

    }

    public String generatePojo(String jsonString) {
        String uuidString = UUID.randomUUID().toString().replace("-", "");
        String generatedPackageName = this.packageName + uuidString;
        Generator generator = null;
        try {
            generator = new Generator("output", generatedPackageName, null, null);
            generator.generateClasses(className, jsonString);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return generatedPackageName;
    }

    public void cleanup(String packageName) {
        File outputDirectory = new File("output/" + packageName.replace(".", "/"));
        if(outputDirectory.exists()) {
            try {
                FileUtils.forceDelete(outputDirectory);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}

