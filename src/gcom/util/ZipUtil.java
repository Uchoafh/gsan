package gcom.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Esta classe re�ne fun��es que manipulam um arquivo zip no sistema
 * 
 * @author Rodrigo Silveira
 * @date 19/05/2006
 */
public class ZipUtil {

	/**
	 * Adiciona o arquivo especificado ao zipOutputStream que representa o
	 * arquivo zip
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/05/2006
	 * 
	 * @param zipFile
	 *            Stream que representa o arquivo zip
	 * @param file
	 *            Arquivo a ser adicionado no arquivo zip
	 * @throws IOException
	 */
	public static void adicionarArquivo(ZipOutputStream zipFile, File file)
			throws IOException {

		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int INPUT_BUFFER_SIZE = 1024;
		byte[] temp = new byte[INPUT_BUFFER_SIZE];
		int numBytesRead = 0;

		while ((numBytesRead = inputStream.read(temp, 0, INPUT_BUFFER_SIZE)) != -1) {
			baos.write(temp, 0, numBytesRead);
		}

		inputStream.close();
		inputStream = null;

		byte[] data = baos.toByteArray();

		ZipEntry zen = new ZipEntry(file.getName());
		zipFile.putNextEntry(zen);
		zipFile.write(data, 0, data.length);
		zipFile.closeEntry();
	}

	/**
	 * Adiciona um diret�rio a um arquivo zip especificado
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/05/2006
	 * 
	 * @param dir2zip
	 *            Diret�rio que ser� adicionado ao arquivo zip
	 * @param zos
	 *            Stream que representa o arquivo zip
	 * @throws IOException
	 */
	public static void adicionarPasta(File dir2zip, ZipOutputStream zos)
			throws IOException {

		String[] dirList = dir2zip.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;

		for (int i = 0; i < dirList.length; i++) {
			File f = new File(dir2zip, dirList[i]);
			if (f.isDirectory()) {
				adicionarPasta(f, zos);

				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			ZipEntry anEntry = new ZipEntry(f.getPath());

			zos.putNextEntry(anEntry);

			while ((bytesIn = fis.read(readBuffer)) != -1) {
				zos.write(readBuffer, 0, bytesIn);
			}

			fis.close();
		}

	}
	
	/*
	  Adiciona o arquivo especificado ao zipOutputStream que representa o
	 * arquivo zip
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/05/2006
	 * 
	 * @param zipFile
	 *            Stream que representa o arquivo zip
	 * @param file
	 *            Arquivo a ser adicionado no arquivo zip
	 * @throws IOException
	 */
	public static void adicionarArquivo(GZIPOutputStream zipFile, File file)
			throws IOException {

		FileInputStream inputStream = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int INPUT_BUFFER_SIZE = 1024;
		byte[] temp = new byte[INPUT_BUFFER_SIZE];
		int numBytesRead = 0;

		while ((numBytesRead = inputStream.read(temp, 0, INPUT_BUFFER_SIZE)) != -1) {
			baos.write(temp, 0, numBytesRead);
		}

		inputStream.close();
		inputStream = null;

		byte[] data = baos.toByteArray();

//		ZipEntry zen = new ZipEntry(file.getName());
//		zipFile.putNextEntry(zen);
		zipFile.write(data, 0, data.length);
		zipFile.close();
//		zipFile.closeEntry();
	}

	/*
	 * @TODO - COSANPA
	 * 
	 * M�todo para descomprimir um arquivo com extens�o Gzip (.gz)
	 * 
	 * @author Felipe Santos
	 * 
	 * @date 26/05/2011
	 * 
	 * @param arquivoGz
	 */
	public static File descomprimirGzip(File arquivoGz) {
		File file = null;

		try {
			
			String arquivoEntradaNome = arquivoGz.getAbsolutePath();
			
			// Abre o arquivo comprimido
			GZIPInputStream in = new GZIPInputStream(new FileInputStream(
					arquivoEntradaNome));

			// Abre o arquivo de sa�da
			String arquivoSaidaNome = arquivoEntradaNome.replace(".gz", "").trim();
			
			OutputStream out = new FileOutputStream(arquivoSaidaNome);			

			// Transfere os bytes do arquivo comprimido para o arquivo de sa�da
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			file = new File(arquivoSaidaNome);
			
			// Fecha os arquivos
			in.close();
			out.close();
			
			//Deleta o arquivo antigo
			arquivoGz.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}
	
	/*
	 * @TODO - COSANPA
	 * 
	 * M�todo para comprimir um arquivo com extens�o Gzip (.gz)
	 * 
	 * @author Felipe Santos
	 * 
	 * @date 26/05/2011
	 * 
	 * @param arquivoGz
	 */
	public static File comprimirGzip(File arquivoOriginal) throws IOException {
		
		String comprimidoTipo = arquivoOriginal.getAbsolutePath()+".gz";
		
		File comprimido = new File(comprimidoTipo);
		
		InputStream is = new FileInputStream(arquivoOriginal);
		GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(
				comprimido));
		byte[] buffer = new byte[16 * 1024];
		for (int nBytesLidos = is.read(buffer); nBytesLidos > 0; nBytesLidos = is
				.read(buffer)) {
			gzos.write(buffer, 0, nBytesLidos);
		}
		
		is.close();
		gzos.close();
		
		return comprimido;
	}

}
