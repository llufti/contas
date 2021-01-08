package upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

public class FileUploadView {
    private boolean controle = false;    
    static boolean controlar = true;
    private static String nomeArquivo;   
    
    
    public FileUploadView() {        
        setNomeArquivo("teste.jpg");
    }
    
    private String realPath;
    
    private UploadedFile file;
    
    public UploadedFile getFile() {
        return file;
    }
    
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        realPath = FacesContext.getCurrentInstance()
                .getExternalContext().getRealPath("/");
       
        byte[] conteudo = event.getFile().getContent();
        FileOutputStream fos;
        try {
            nomeArquivo = event.getFile().getFileName();            
            fos = new FileOutputStream("/opt/glassfish4/glassfish/domains/domain1/applications/GestaoCroche/resources/images" + "/" + event.getFile().getFileName());
            fos.write(conteudo);
            fos.close();
            setControle(true);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUploadView.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }    
   
    
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public static void setNomeArquivo(String nomeArquivo) {
        FileUploadView.nomeArquivo = nomeArquivo;
    }
    
    public String getRealPath() {
        return realPath;
    }

    public boolean isControle() {
        return controle;
    }

    public void setControle(boolean controle) {
        this.controle = controle;
    }

    public boolean isControlar() {
        return controlar;
    }

    public void setControlar(boolean controlar) {
        this.controlar = controlar;
    }

    
    
    
}
