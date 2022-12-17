package org.ternlang.core.type.extend;

import org.ternlang.common.Consumer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileExtension {

   private static final String EXTENSION_ZIP = ".zip";
   private static final String DIGEST_ALGORITHM = "SHA-256";
   private static final String KEY_ALGORITHM = "AES";
   private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

   private final InputStreamExtension streams;

   public FileExtension() {
      this.streams = new InputStreamExtension();
   }

   public InputStream stream(File file) throws IOException {
      return new FileInputStream(file);
   }

   public Reader reader(File file) throws IOException {
      InputStream stream = new FileInputStream(file);
      Reader reader = new InputStreamReader(stream);

      return new BufferedReader(reader);
   }

   public Reader reader(File file, String charset) throws IOException {
      InputStream stream = new FileInputStream(file);
      Reader reader = new InputStreamReader(stream, charset);

      return new BufferedReader(reader);
   }

   public List<String> contains(File file, String pattern) throws IOException {
      FileReader source = new FileReader(file);
      LineNumberReader reader = new LineNumberReader(source);
      List<String> lines = new ArrayList<String>();

      try {
         while (true) {
            String line = reader.readLine();

            if (line == null) {
               return lines;
            }
            if (line.contains(pattern)) {
               lines.add(line);
            }
         }
      } finally {
         reader.close();
      }
   }

   public List<String> search(File file, String pattern) throws IOException {
      FileReader source = new FileReader(file);
      LineNumberReader reader = new LineNumberReader(source);
      List<String> lines = new ArrayList<String>();

      try {
         while (true) {
            String line = reader.readLine();

            if (line == null) {
               return lines;
            }
            if (line.matches(pattern)) {
               lines.add(line);
            }
         }
      } finally {
         reader.close();
      }
   }

   public void writeBytes(File file, byte[] data) throws IOException {
      OutputStream stream = new FileOutputStream(file);

      try {
         if (data.length > 0) {
            stream.write(data);
         }
      } finally {
         stream.close();
      }
   }

   public void writeText(File file, String text) throws IOException {
      OutputStream stream = new FileOutputStream(file);

      try {
         byte[] data = text.getBytes();

         if (data.length > 0) {
            stream.write(data);
         }
      } finally {
         stream.close();
      }
   }

   public void writeText(File file, String text, String encoding) throws IOException {
      OutputStream stream = new FileOutputStream(file);

      try {
         byte[] data = text.getBytes(encoding);

         if (data.length > 0) {
            stream.write(data);
         }
      } finally {
         stream.close();
      }
   }

   public byte[] readBytes(File file) throws IOException {
      InputStream stream = new FileInputStream(file);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] data = new byte[1024];
      int count = 0;

      try {
         while ((count = stream.read(data)) != -1) {
            buffer.write(data, 0, count);
         }
         return buffer.toByteArray();
      } finally {
         stream.close();
      }
   }

   public String readText(File file) throws IOException {
      InputStream stream = new FileInputStream(file);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] data = new byte[1024];
      int count = 0;

      try {
         while ((count = stream.read(data)) != -1) {
            buffer.write(data, 0, count);
         }
         return buffer.toString();
      } finally {
         stream.close();
      }
   }

   public String readText(File file, String encoding) throws IOException {
      InputStream stream = new FileInputStream(file);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] data = new byte[1024];
      int count = 0;

      try {
         while ((count = stream.read(data)) != -1) {
            buffer.write(data, 0, count);
         }
         return buffer.toString(encoding);
      } finally {
         stream.close();
      }
   }

   public List<String> readLines(File file) throws IOException {
      FileReader reader = new FileReader(file);
      LineNumberReader iterator = new LineNumberReader(reader);
      List<String> lines = new ArrayList<String>();

      try {
         while (true) {
            String line = iterator.readLine();

            if (line == null) {
               return lines;
            }
            lines.add(line);
         }
      } finally {
         iterator.close();
      }
   }

   public List<String> readLines(File file, String encoding) throws IOException {
      InputStream stream = new FileInputStream(file);
      Reader reader = new InputStreamReader(stream, encoding);
      LineNumberReader iterator = new LineNumberReader(reader);
      List<String> lines = new ArrayList<String>();

      try {
         while (true) {
            String line = iterator.readLine();

            if (line == null) {
               return lines;
            }
            lines.add(line);
         }
      } finally {
         iterator.close();
      }
   }

   public void forEachLine(File file, Consumer<String, ?> consumer) throws IOException {
      InputStream stream = new FileInputStream(file);
      Reader reader = new InputStreamReader(stream);
      LineNumberReader iterator = new LineNumberReader(reader);

      try {
         while (true) {
            String line = iterator.readLine();

            if (line == null) {
               break;
            }
            consumer.consume(line);
         }
      } finally {
         iterator.close();
      }
   }

   public void forEachLine(File file, String encoding, Consumer<String, ?> consumer) throws IOException {
      InputStream stream = new FileInputStream(file);
      Reader reader = new InputStreamReader(stream, encoding);
      LineNumberReader iterator = new LineNumberReader(reader);

      try {
         while (true) {
            String line = iterator.readLine();

            if (line == null) {
               break;
            }
            consumer.consume(line);
         }
      } finally {
         iterator.close();
      }
   }

   public List<File> findFiles(File directory, String pattern) throws IOException {
      List<File> files = new ArrayList<File>();

      if (directory.exists()) {
         File[] list = directory.listFiles();

         if (list != null) {
            for (File file : list) {
               File normal = file.getCanonicalFile();
               String name = normal.getName();

               if (name.matches(pattern)) {
                  files.add(normal);
               }
               if (file.isDirectory()) {
                  List<File> children = findFiles(normal, pattern);

                  if (!children.isEmpty()) {
                     files.addAll(children);
                  }
               }
            }
         }
      }
      return files;
   }

   public List<File> findFiles(File directory, FileFilter filter) throws IOException {
      List<File> files = new ArrayList<File>();

      if (directory.exists()) {
         File[] list = directory.listFiles();

         if (list != null) {
            for (File file : list) {
               File normal = file.getCanonicalFile();

               if (filter.accept(normal)) {
                  files.add(normal);
               }
               if (file.isDirectory()) {
                  List<File> children = findFiles(normal, filter);

                  if (!children.isEmpty()) {
                     files.addAll(children);
                  }
               }
            }
         }
      }
      return files;
   }

   public List<String> findPaths(File directory, String pattern) throws IOException {
      List<String> paths = new ArrayList<String>();

      if (directory.exists()) {
         List<File> files = findFiles(directory, pattern);

         for (File file : files) {
            String path = file.getCanonicalPath();

            if (path != null) {
               paths.add(path);
            }
         }
      }
      return paths;
   }

   public List<String> findPaths(File directory, FileFilter filter) throws IOException {
      List<String> paths = new ArrayList<String>();

      if (directory.exists()) {
         List<File> files = findFiles(directory, filter);

         for (File file : files) {
            String path = file.getCanonicalPath();

            if (path != null) {
               paths.add(path);
            }
         }
      }
      return paths;
   }

   public File resolve(File file, String name) {
      File from = file.getAbsoluteFile();
      Path path = from.toPath();

      if (!name.isEmpty()) {
         return path.resolve(name).toFile();
      }
      return from;
   }

   public File resolveSibling(File file, String name) {
      File from = file.getAbsoluteFile();
      Path path = from.toPath();

      if (!name.isEmpty()) {
         return path.resolveSibling(name).toFile();
      }
      return from;
   }

   public boolean moveTo(File from, String output) {
      File to = resolveSibling(from, output);

      if (!from.exists()) {
         throw new IllegalArgumentException("Path " + from + " does not exist");
      }
      if (!from.equals(to)) {
         return moveTo(from, to);
      }
      return true;
   }

   public boolean moveTo(File from, File to) {
      if (!from.exists()) {
         throw new IllegalArgumentException("File " + from + " does not exist");
      }
      if (to.exists()) {
         throw new IllegalArgumentException("File " + to + " already exists");
      }
      if (!from.equals(to)) {
         if (to.isDirectory()) {
            String name = from.getName();
            File file = resolve(to, name);

            return from.renameTo(file);
         }
         return from.renameTo(to);
      }
      return true;
   }

   public File copyTo(File from, String output) {
      File result = resolveSibling(from, output);

      if (!from.exists()) {
         throw new IllegalArgumentException("Path " + from + " does not exist");
      }
      if (!from.equals(result)) {
         return copyTo(from, result);
      }
      return from;
   }

   public File copyTo(File from, File to) {
      if (!from.exists()) {
         throw new IllegalArgumentException("File " + from + " does not exist");
      }
      if (!from.isFile()) {
         throw new IllegalArgumentException("File " + from + " is not a file");
      }
      try {
         String name = from.getName();
         File result = to.isDirectory() ? to.toPath().resolve(name).toFile() : to;

         if (!from.equals(result)) {
            FileInputStream source = new FileInputStream(from);
            OutputStream destination = new FileOutputStream(result);
            BufferedOutputStream buffer = new BufferedOutputStream(destination, 8192);

            try {
               streams.copyTo(source, buffer);
            } finally {
               buffer.close();
            }
         }
         return result;
      } catch (Exception e) {
         throw new IllegalArgumentException("Unable to copy from " + from + " to " + to, e);
      }
   }

   public File zip(File input) throws IOException {
      String name = input.getName();
      File result = resolveSibling(input, name + EXTENSION_ZIP);

      if (!input.exists()) {
         throw new IllegalArgumentException("Path " + input + " does not exist");
      }
      return zip(input, result);
   }

   public File zip(File input, String output) throws IOException {
      File result = resolveSibling(input, output);

      if (!input.exists()) {
         throw new IllegalArgumentException("Path " + input + " does not exist");
      }
      return zip(input, result);
   }

   public File zip(File input, File output) throws IOException {
      if (!input.exists()) {
         throw new IllegalArgumentException("Path " + input + " does not exist");
      }
      String name = output.getName();

      if (!name.endsWith(EXTENSION_ZIP)) {
         throw new IllegalArgumentException("Output file " + output + " does not end with " + EXTENSION_ZIP);
      }
      OutputStream stream = new FileOutputStream(output);
      ZipOutputStream out = new ZipOutputStream(stream);
      Set<String> done = new HashSet<>();

      try {
         List<File> files = input.isDirectory() ? findFiles(input, ".*") : Arrays.asList(input);
         String path = input.isDirectory() ? input.getCanonicalPath() : input.getParent();
         int length = path.length();

         for (File entry : files) {
            String child = entry.getCanonicalPath();
            String relative = child.substring(length).replace(File.separatorChar, '/');

            if (relative.startsWith("/")) {
               relative = relative.substring(1); // /org/domain/Type.class -> org/domain/Type.class
            }
            if (done.add(relative)) {
               if (entry.isFile()) {
                  ZipEntry element = new ZipEntry(relative);
                  FileInputStream source = new FileInputStream(entry);

                  out.putNextEntry(element);
                  streams.copyTo(source, out);
               } else if (entry.isDirectory()) {
                  ZipEntry element = new ZipEntry(relative + "/");

                  out.putNextEntry(element);
               }
            }
         }
      } finally {
         out.close();
         stream.close();
      }
      return output;
   }

   public File unzip(File input) throws IOException {
      if (!input.exists()) {
         throw new IllegalArgumentException("Input file " + input + " does not exist");
      }
      String name = input.getName();

      if (!name.endsWith(EXTENSION_ZIP)) {
         throw new IllegalArgumentException("Input file " + input + " does not end with " + EXTENSION_ZIP);
      }
      File path = input.getAbsoluteFile();
      File parent = path.getParentFile();

      return unzip(input, parent);
   }

   public File unzip(File input, File output) throws IOException {
      if (!output.exists()) {
         throw new IllegalArgumentException("Path " + output + " does not exist");
      }
      if (!output.isDirectory()) {
         throw new IllegalArgumentException("Path " + output + " is not a  directory");
      }
      if (!input.exists()) {
         throw new IllegalArgumentException("Input file " + input + " does not exist");
      }
      String name = input.getName();

      if (!name.endsWith(EXTENSION_ZIP)) {
         throw new IllegalArgumentException("Input file " + input + " does not end with " + EXTENSION_ZIP);
      }
      FileInputStream stream = new FileInputStream(input);
      ZipInputStream source = new ZipInputStream(stream);

      try {
         ZipEntry entry = source.getNextEntry();

         while (entry != null) {
            String element = entry.getName();
            File file = output.toPath().resolve(element).toFile();

            if (entry.isDirectory()) {
               file.mkdirs();
            } else {
               File parent = file.getParentFile();

               if (parent.exists() || parent.mkdirs()) {
                  OutputStream to = new FileOutputStream(file);

                  try {
                     streams.copyTo(source, to);
                  } finally {
                     to.close();
                  }
               }
            }
            try {
               entry = source.getNextEntry();
            } catch (IOException e) {
               return output;
            }
         }
      } finally {
         source.close();
         stream.close();
      }
      return output;
   }

   public File encrypt(File file, String secret) {
      EncryptKey key = new EncryptKey(secret, false);

      if (!file.exists()) {
         throw new IllegalStateException("File " + file + " does not exist");
      }
      return key.apply(file);
   }

   public File decrypt(File file, String secret) throws Exception {
      EncryptKey key = new EncryptKey(secret, true);

      if (!file.exists()) {
         throw new IllegalStateException("File " + file + " does not exist");
      }
      return key.apply(file);
   }

   private class EncryptKey {

      private final String secret;
      private final byte[] data;
      private final boolean decrypt;

      public EncryptKey(String secret, boolean decrypt) {
         this.data = secret.getBytes(StandardCharsets.UTF_8);
         this.decrypt = decrypt;
         this.secret = secret;
      }

      public File apply(File file) {
         try {
            String path = file.getCanonicalPath();
            MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] encoded = digest.digest(data);
            long time = System.currentTimeMillis();

            if (secret.isEmpty()) {
               throw new IllegalStateException("Secret not strong enough");
            }
            File result = new File(path + ".tmp." + time);
            InputStream source = new FileInputStream(file);
            OutputStream stream = new FileOutputStream(result);
            OutputStream destination = new EncryptOutputStream(stream, cipher, decrypt);
            SecretKey key = new SecretKeySpec(encoded, KEY_ALGORITHM);

            try {
               cipher.init(decrypt ? Cipher.DECRYPT_MODE : Cipher.ENCRYPT_MODE, key);
               streams.copyTo(source, destination);
               destination.close();
               source.close();
               return copyTo(result, file);
            } finally {
               destination.close();
               source.close();
               result.delete();
            }
         } catch (Exception e) {
            throw new IllegalStateException("Failed to encrypt " + file, e);
         }
      }

      private class EncryptOutputStream extends FilterOutputStream {

         private final Cipher cipher;
         private final boolean decrypt;
         private boolean closed;

         public EncryptOutputStream(OutputStream output, Cipher cipher, boolean decrypt) {
            super(output);
            this.cipher = cipher;
            this.decrypt = decrypt;
         }

         @Override
         public void write(int octet) throws IOException {
            byte[] swap = new byte[]{(byte) octet};
            byte[] buffer = cipher.update(swap, 0, 1);

            if (buffer != null) {
               out.write(buffer);
            }
         }

         @Override
         public void write(byte data[]) throws IOException {
            write(data, 0, data.length);
         }

         @Override
         public void write(byte data[], int off, int len) throws IOException {
            byte[] buffer = cipher.update(data, off, len);

            if (buffer != null) {
               out.write(buffer);
            }
         }

         @Override
         public void flush() throws IOException {
            out.flush();
         }

         @Override
         public void close() throws IOException {
            if (!closed) {
               try {
                  byte[] buffer = cipher.doFinal();

                  if (buffer != null) {
                     out.write(buffer);
                     out.flush();
                  }
               } catch (Throwable cause) {
                  if (decrypt) {
                     throw new IOException("Failed to decrypt", cause);
                  } else {
                     throw new IOException("Failed to encrypt", cause);
                  }
               }
               out.close();
               closed = true;
               return;
            }
         }
      }
   }
}