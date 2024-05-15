import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Main caseIdAmtchanges = new Main();
        //String fileName = "C:\\Users\\NirmalaDeviKaliappan\\Desktop\\CaseIdChange\\testcases\\PaymarkC2.txt";
        String fileName = "C:\\Users\\NirmalaDeviKaliappan\\Desktop\\CaseIdChange\\testcases\\TermDraft.txt";
        caseIdAmtchanges.stepIDcaseIDAmountChanges(fileName);
    }

    public void stepIDcaseIDAmountChanges(String fileName) {

        try {
            FileInputStream fis = new FileInputStream(fileName);
            String data = IOUtils.toString(fis, "UTF-8");
            String[] caseIdCollection = data.split("\n");
            String casecaseName;
            List<String> lines = Files.readAllLines(Path.of(fileName));

            for (int i = 0; i < caseIdCollection.length; i++) {
                if (caseIdCollection[i].startsWith("CaseCaseName")) {
                    String regex = "\\d.*";
                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(caseIdCollection[i]);
                    while (matcher.find()) {
                        casecaseName = "";
                        System.out.println("case casename match: " + matcher.group(0));
                        if (matcher.group(0).contains(".") && matcher.group(0).matches(".*[A-Z].*")) {
                            if (matcher.group(0).split("\\.").length > 2) {
                                if (matcher.group(0).split("\\.")[1].matches("\\d.*")) {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    Boolean b = matcher.group(0).split("\\.")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]);
                                                    }
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    Boolean b = matcher.group(0).split("\\.")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]);
                                                    }
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    if (lines.get(j + 6).startsWith("CaseSteps")) {
                                                        String[] caseSteps = lines.get(j + 6).split("\\D+");
                                                        stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    }
                                                    break;
                                                } else {
//                                                    String casename_reg ="(\\d+\\.+\\s*)";
                                                    if (matcher.group(0).split("\\.").length > 2) {
                                                        Boolean b = matcher.group(0).split("\\.")[1].matches(".*[A-Z].*");
                                                        if (!b) {
                                                            casecaseName = (matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]).strip();
                                                            System.out.println("case amount: " + matcher.group(0).split("\\.")[0] + "." + matcher.group(0).split("\\.")[1]);
                                                        }
                                                    }
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    System.out.println("case name match: " + casecaseName);
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (matcher.group(0).split("\\.").length > 1) {
                                if (matcher.group(0).split("\\.")[1].matches("\\d.")) {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if ((lines.size() - 6) >= j + 6) {
                                            if (lines.get(j).contains(matcher.group(0))) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split("\\.")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split("\\.")[0]);
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split("\\.")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split("\\.")[0]);
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                } else {
                                                    if (matcher.group(0).split("\\.").length > 1) {
                                                        System.out.println("matcher group splitted: " + casecaseName);
                                                        casecaseName = matcher.group(0).split("\\.")[0].strip();
                                                    }
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    System.out.println("case name match: " + casecaseName);
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split(" ")[0]);
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps, lines, casecaseName, fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split(" ")[0]);
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else {

                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("matcher group splitted-1: " + casecaseName);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    System.out.println("case name match: " + casecaseName);
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        } else if (matcher.group(0).contains(".")) {
                            for (int j = 0; j < lines.size(); j++) {
                                if (lines.get(j).contains(matcher.group(0))) {
                                    if ((lines.size() - 6) >= j + 6) {
                                        if (lines.get(j + 4).startsWith("CaseAmount")) {
                                            casecaseName = (matcher.group(0)).strip();
                                            System.out.println("case amount: " + casecaseName);
                                            lines.set(j + 4, "CaseAmount=" + casecaseName);
                                            Files.write(Path.of(fileName), lines);
                                            String[] caseSteps = lines.get(j + 5).split("\\D+");
                                            stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                            break;
                                        } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                            //System.out.println("case amount found: " + lines.get(j));
                                            casecaseName = (matcher.group(0)).strip();
                                            System.out.println("case amount: " + casecaseName);
                                            lines.set(j + 5, "CaseAmount=" + casecaseName);
                                            Files.write(Path.of(fileName), lines);
                                            String[] caseSteps = lines.get(j + 5).split("\\D+");
                                            stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                            break;
                                        } else {
                                            casecaseName = (matcher.group(0)).strip();
                                            System.out.println("matcher group splitted-2: " + casecaseName);
                                            System.out.println("case name match: " + casecaseName);
                                            String[] caseSteps = lines.get(j + 5).split("\\D+");
                                            stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                            break;
                                        }
                                    }
                                }
                            }

                        } else {
                            if (matcher.group(0).split(" ").length > 2) {
                                if (matcher.group(0).split(" ")[1].matches("\\d.")) ;
                                {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    Boolean b = matcher.group(0).split(" ")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    }
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName.replaceAll("\\D+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    Boolean b = matcher.group(0).split(" ")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    } else {
                                                        casecaseName = matcher.group(0).split(" ")[0];
                                                    }
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName.replaceAll("\\D+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else {

                                                    Boolean b = matcher.group(0).split(" ")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                        System.out.println("case amount-2: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    }

                                                    System.out.println("case name match: " + casecaseName);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (matcher.group(0).split(" ").length > 1) {
                                if (matcher.group(0).split(" ")[1].matches("\\d.")) {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    Boolean b = matcher.group(0).split("\\.")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        //System.out.println("case amount found: " + lines.get(j));
                                                        casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    }
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName.replaceAll("\\D+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                   stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                    System.out.println("case amount: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName.replaceAll("\\D+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else {

                                                    Boolean b = matcher.group(0).split(" ")[1].matches(".*[A-Z].*");
                                                    if (!b) {
                                                        casecaseName = (matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]).strip();
                                                        System.out.println("case amount: " + matcher.group(0).split(" ")[0] + "." + matcher.group(0).split(" ")[1]);
                                                    }

                                                    System.out.println("case name match: " + casecaseName);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split(" ")[0]);
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName);
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                                if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("case amount: " + matcher.group(0).split(" ")[0]);
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName.replaceAll("\\D+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else {
                                                    casecaseName = matcher.group(0).split(" ")[0].strip();
                                                    System.out.println("case name match: " + casecaseName);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (matcher.group(0).split(" ")[0].matches(".*[0-9].*")) {
                                    for (int j = 0; j < lines.size(); j++) {
                                        if (lines.get(j).contains(matcher.group(0))) {
                                            if ((lines.size() - 6) >= j + 6) {
                                                if (lines.get(j + 4).startsWith("CaseAmount")) {
                                                    //System.out.println("case amount found: " + lines.get(j));
                                                    casecaseName = (matcher.group(0)).strip();
                                                    System.out.println("case amount: " + casecaseName.replaceAll("\\d+", ""));
                                                    lines.set(j + 4, "CaseAmount=" + casecaseName.replaceAll("\\d+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else if (lines.get(j + 5).startsWith("CaseAmount")) {
                                                    casecaseName = (matcher.group(0)).strip();
                                                    System.out.println("case amount: " + casecaseName.replaceAll("\\d+", ""));
                                                    lines.set(j + 5, "CaseAmount=" + casecaseName.replaceAll("\\d+", ""));
                                                    Files.write(Path.of(fileName), lines);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                   stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                } else {
                                                    casecaseName = (matcher.group(0)).strip();
                                                    System.out.println("case name match: " + casecaseName);
                                                    String[] caseSteps = lines.get(j + 5).split("\\D+");
                                                    stepAmountChanges(caseSteps,lines,casecaseName,fileName);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            System.out.println("exception in reading: " + exception.getStackTrace()[0].getLineNumber());
        }

    }

    public void stepAmountChanges(String[] caseSteps, List<String> lines, String casecaseName, String fileName) {
        try {
            for (int k = 1; k < caseSteps.length; k++) {
                for (int l = 0; l < lines.size(); l++) {
                    if ((lines.get(l).contains("StepId=" + caseSteps[k])) && lines.get(l + 6).startsWith("StepAmount=")) {
                        System.out.println("case steps: " + caseSteps[k]);
                        if (lines.get(l + 6).startsWith("StepAmount=")) {
                            System.out.println("stepid matched: " + lines.get(l) + " step amount: " + lines.get(l + 6) + "changed to :" + casecaseName);
                            lines.set(l + 6, "StepAmount=" + casecaseName);
                            Files.write(Path.of(fileName), lines);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in step amount changes: " + e.getStackTrace()[0].getLineNumber());
        }
    }


//    public void caseIDAmountChanges(String fileName) {
//
//        try {
//            FileInputStream fis = new FileInputStream(fileName);
//            String data = IOUtils.toString(fis, "UTF-8");
//            String[] caseIdCollection = data.split("\n");
//            String casecaseName;
//            List<String> lines = Files.readAllLines(Path.of(fileName));
//
//            for (int i = 0; i < caseIdCollection.length; i++) {
//                if (caseIdCollection[i].startsWith("CaseId")) {
//                    String regex = "\\d.*";
//                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//                    Matcher matcher = pattern.matcher(caseIdCollection[i]);
//                    while (matcher.find()) {
//                        casecaseName = "";
//                        System.out.println("Full match: " + matcher.group(0));
//                        casecaseName = matcher.group(0);
//
//                        for (int j = 0; j < lines.size(); j++) {
//                            if ((lines.get(j).split("=").length) > 1 && lines.get(j).split("=")[0].startsWith("CaseId") && lines.get(j).split("=")[1].equals(casecaseName)) {
//                                System.out.println("Case id found: " + lines.get(j));
//                                if (lines.get(j + 3).split("=")[0].startsWith("CaseCaseName")) {
//                                    String[] CasecaseName = lines.get(j + 3).split(" ");
//                                    if (CasecaseName.length > 3) {
//                                        String changedCaseName = "CaseCaseName=" + casecaseName + " " + CasecaseName[1] + " " + CasecaseName[2] + " " + CasecaseName[3];
//                                        System.out.println("changed CaseCaseName: " + changedCaseName);
//                                        lines.set(j + 3, changedCaseName);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    } else if (CasecaseName.length > 2) {
//                                        String changedCaseName = "CaseCaseName=" + casecaseName + " " + CasecaseName[1] + " " + CasecaseName[2];
//                                        System.out.println("changed CaseCaseName: " + changedCaseName);
//                                        lines.set(j + 3, changedCaseName);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    } else {
//                                        String changedCaseName = "CaseCaseName=" + " " + casecaseName;
//                                        System.out.println("changed CaseCaseName: " + changedCaseName);
//                                        lines.set(j + 3, changedCaseName);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception exception) {
//            System.out.println("exception in reading: " + exception.getStackTrace()[0].getLineNumber());
//        }
//
//    }
//
//    public void stepIDAmountChanges(String fileName) {
//
//        try {
//            FileInputStream fis = new FileInputStream(fileName);
//            String data = IOUtils.toString(fis, "UTF-8");
//            String[] caseIdCollection = data.split("\n");
//            String casecaseName;
//            List<String> lines = Files.readAllLines(Path.of(fileName));
//
//            for (int i = 0; i < caseIdCollection.length; i++) {
//                if (caseIdCollection[i].startsWith("StepId")) {
//                    String regex = "\\d.*";
//                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//                    Matcher matcher = pattern.matcher(caseIdCollection[i]);
//                    while (matcher.find()) {
//                        casecaseName = "";
//                        System.out.println("Full match: " + matcher.group(0));
//                        casecaseName = matcher.group(0);
//                        for (int j = 0; j < lines.size(); j++) {
//                            if ((lines.get(j).split("=").length) > 1 && lines.get(j).split("=")[0].startsWith("StepId")) {
//                                //System.out.println("Step id found: " + lines.get(j));
//                                if (lines.get(j).split("=")[1].equals(casecaseName)) {
//                                    if (lines.get(j + 5).startsWith("StepAmount")) {
//                                        System.out.println("step amount changed: " + casecaseName);
//                                        lines.set(j + 5, "StepAmount=" + casecaseName);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    } else if (lines.get(j + 6).startsWith("StepAmount")) {
//                                        System.out.println("step amount changed: " + casecaseName);
//                                        lines.set(j + 6, "StepAmount=" + casecaseName);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception exception) {
//            System.out.println("exception in reading: " + exception.getStackTrace()[0].getLineNumber());
//        }
//
//    }
//
//    public void caseStepAmountChanges(String fileName) {
//        try {
//            FileInputStream fis = new FileInputStream(fileName);
//            String data = IOUtils.toString(fis, "UTF-8");
//            String[] caseIdCollection = data.split("\n");
//            String caseStepNum;
//            List<String> lines = Files.readAllLines(Path.of(fileName));
//
//            for (int i = 0; i < caseIdCollection.length; i++) {
//                if (caseIdCollection[i].startsWith("CaseSteps")) {
//                    String regex = "\\d.";
//                    Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
//                    Matcher matcher = pattern.matcher(caseIdCollection[i]);
//
//                    while (matcher.find()) {
//                        caseStepNum = "";
//                        System.out.println("Full match: " + matcher.group(0).replace("\r", "").replace("\"", ""));
//                        caseStepNum = matcher.group(0).replace("\r", "").replace("\"", "");
//                        for (int j = 0; j < lines.size(); j++) {
//                            if ((lines.get(j).split("=").length) > 1 && lines.get(j).split("=")[0].startsWith("StepId")) {
//                                //System.out.println("Step id found: " + lines.get(j));
//                                if (lines.get(j).split("=")[1].equals(caseStepNum)) {
//                                    if (lines.get(j + 5).startsWith("StepAmount")) {
//                                        System.out.println("step amount changed: " + caseStepNum);
//                                        lines.set(j + 5, "StepAmount=" + caseStepNum);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    } else if (lines.get(j + 6).startsWith("StepAmount")) {
//                                        System.out.println("step amount changed: " + caseStepNum);
//                                        lines.set(j + 6, "StepAmount=" + caseStepNum);
//                                        Files.write(Path.of(fileName), lines);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception exception) {
//            System.out.println("exception in reading: " + exception.getStackTrace()[0].getLineNumber());
//        }
//    }

}