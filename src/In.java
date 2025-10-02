package src;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class In {

    private static final String CHARSET_NAME = "UTF-8";

    private static final Locale LOCALE = Locale.US;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

    private static final Pattern EMPTY_PATTERN = Pattern.compile("");

    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");

    private Scanner scanner;

    public In() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

    public In(Socket socket) {
        if (socket == null) throw new IllegalArgumentException("socket argument is null");
        try {
            InputStream is = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("could not open socket: " + socket, ioe);
        }
    }

    public In(URL url) {
        if (url == null) throw new IllegalArgumentException("url argument is null");
        try {
            URLConnection site = url.openConnection();
            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("could not read URL: '" + url + "'", ioe);
        }
    }

    public In(File file) {
        if (file == null) throw new IllegalArgumentException("file argument is null");
        try {
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException ioe) {;
            throw new IllegalArgumentException("could not read file: " + file, ioe);
        }
    }

    public In(String name) {
        if (name == null) throw new IllegalArgumentException("argument is null");
        if (name.length() == 0) throw new IllegalArgumentException("argument is the empty string");
        try {
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            URL url = getClass().getResource(name);

            if (url == null) {
                url = getClass().getClassLoader().getResource(name);
            }

            if (url == null) {
                URI uri = new URI(name);
                if (uri.isAbsolute()) url = uri.toURL();
                else throw new IllegalArgumentException("could not read: '" + name + "'");
                url = new URL(name);
            }

            URLConnection site = url.openConnection();

            InputStream is     = site.getInputStream();
            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        }
        catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("could not read: '" + name + "'");
        }
    }

    public In(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("scanner argument is null");
        this.scanner = scanner;
    }

    public boolean exists()  {
        return scanner != null;
    }

    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    public char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        try {
            String ch = scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
                + " Please contact the authors.";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'char' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public String readAll() {
        if (!scanner.hasNextLine())
            return "";

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN); 
        return result;
    }

    public String readString() {
        try {
            return scanner.next();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'String' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public int readInt() {
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read an 'int' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public double readDouble() {
        try {
            return scanner.nextDouble();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'double' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public float readFloat() {
        try {
            return scanner.nextFloat();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'float' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public long readLong() {
        try {
            return scanner.nextLong();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'long' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public short readShort() {
        try {
            return scanner.nextShort();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'short' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public byte readByte() {
        try {
            return scanner.nextByte();
        }
        catch (InputMismatchException e) {
            String token = scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attemps to read a 'byte' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public boolean readBoolean() {
        try {
            String token = readString();
            if ("true".equalsIgnoreCase(token))  return true;
            if ("false".equalsIgnoreCase(token)) return false;
            if ("1".equals(token))               return true;
            if ("0".equals(token))               return false;
            throw new InputMismatchException("attempts to read a 'boolean' value from the input stream, "
                                           + "but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from the input stream, "
                                           + "but no more tokens are available");
        }
    }

    public String[] readAllStrings() {
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0)
            return tokens;
        String[] decapitokens = new String[tokens.length-1];
        for (int i = 0; i < tokens.length-1; i++)
            decapitokens[i] = tokens[i+1];
        return decapitokens;
    }

    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[0]);
    }

    public int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Integer.parseInt(fields[i]);
        return vals;
    }

    public long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Long.parseLong(fields[i]);
        return vals;
    }

    public double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

    public void close() {
        scanner.close();
    }

    @Deprecated
    public static int[] readInts(String filename) {
        return new In(filename).readAllInts();
    }

    @Deprecated
    public static double[] readDoubles(String filename) {
        return new In(filename).readAllDoubles();
    }

    @Deprecated
    public static String[] readStrings(String filename) {
        return new In(filename).readAllStrings();
    }

    @Deprecated
    public static int[] readInts() {
        return new In().readAllInts();
    }

    @Deprecated
    public static double[] readDoubles() {
        return new In().readAllDoubles();
    }

    @Deprecated
    public static String[] readStrings() {
        return new In().readAllStrings();
    }

    public static void main(String[] args) {
        In in;
        String urlName = "https://introcs.cs.princeton.edu/java/stdlib/InTest.txt";

        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            System.out.println(in.readAll());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(urlName);
            while (!in.isEmpty()) {
                String s = in.readString();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("./InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("../stdlib/InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("InTest.txt");
            while (!in.isEmpty()) {
                char c = in.readChar();
                System.out.print(c);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println();

        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In("G:\\www\\introcs\\stdlib\\InTest.txt");
            while (!in.isEmpty()) {
                String s = in.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();

    }

}