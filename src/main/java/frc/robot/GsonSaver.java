package frc.robot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import edu.wpi.first.wpilibj.Filesystem;
import java.lang.SecurityManager;
import javax.swing.text.StyledEditorKit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSaver {
    //private static final Path = Filesystem.get blab lablalsi
    // public static final String TRAJECTORY_PATH = "/Jsons/Trajectories/";
    // public static final String TRAJECTORY_CONFIG_PATH = "/Jsons/TrajectoryConfigs/";
    // public static final String OTHER_PATH = "/Jsons/Other/";
    private Gson gson;
    private String directory;
    private ArrayList<String> names;

    public GsonSaver(){ 
        names = new ArrayList<String>();
        Filesystem.getLaunchDirectory().mkdir();
        Filesystem.getDeployDirectory().mkdir();
        directory = Filesystem.getDeployDirectory().toPath().toString();
        System.out.println(directory);
        Filesystem.getDeployDirectory().setExecutable(true, false);
        Filesystem.getDeployDirectory().setReadable(true, false);
        Filesystem.getDeployDirectory().setWritable(true, false);
        
        System.out.println(Filesystem.getDeployDirectory().canWrite() + ", " + Filesystem.getDeployDirectory().canRead() + ", " + Filesystem.getDeployDirectory().canExecute());
        // System.out.println(Filesystem.getLaunchDirectory().canWrite() + ", " + Filesystem.getLaunchDirectory().canRead() + ", " + Filesystem.getLaunchDirectory().canExecute());
        gson = new GsonBuilder()
                    .generateNonExecutableJson()
                    .serializeNulls()
                    .setPrettyPrinting()    
                    .create();
    }
    public void setBuild(GsonBuilder builder){
        gson = builder.create();
    }
    public ArrayList<String> getNames() {
        return names;
    }
    public void saveObject(Object save, String fileName){
        if(this.names.contains(fileName)){
            System.err.println("FILE "+ fileName +" ALREADY EXISTS");
            return;
        }
        else{
            this.names.add(fileName);
        }


        File file = new File(this.directory + fileName + ".json");
        //File file = new File(this.directory + filePath + fileName + ".json");
        file.setExecutable(true, false);
        file.setReadable(true, false);
        file.setWritable(true, false);
        try {   
            file.createNewFile();
        // if(file.createNewFile()){
        //     System.out.println("CREATED: " + fileName);
        // }
        } 
        catch (IOException e1) {
            // TODO Auto-generated catch block
            System.out.println("DID NOT CREATE: " + fileName);
            e1.printStackTrace();
        }
        System.out.println(file.exists() + "WOOOOT");
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(save, writer);
        } catch (IOException e) {
            System.out.println("DID NOT SAVE: " + fileName);
            e.printStackTrace();
        }
    } 
    public <T> T getObject(Class<T> classOfT, String fileName){
        
        if(!this.names.contains(fileName)){
            System.err.println("FILE "+ fileName +" DOSE NOT EXIST");
            return null;
        }
        try (Reader reader = new FileReader(this.directory + fileName + ".json")) {

            // Convert JSON File to Java Object
            return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            System.out.println("Did not get: " + fileName);
            e.printStackTrace();
            return null;
        }
    }
}
