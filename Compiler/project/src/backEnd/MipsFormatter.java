package backEnd;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MipsFormatter {
    public static ArrayList<String> format(ArrayList<Function> functions) {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Instruction> text = new ArrayList<>();
        ArrayList<Instruction> data = new ArrayList<>();
        Set<String> regs = new HashSet<>();
        for (Function func : functions) {
            regs.addAll(
                    RegisterAllocator.getVirtualRegs(func.getCodes())
            );
            ArrayList<Instruction> codes = func.getCodes();
            for (Instruction instruction : codes) {
                if (instruction.getType() == InstructionType.MIPS) {
                    if (instruction.getSection() == MipsSection.DATA) {
                        data.add(instruction);
                    } else {
                        text.add(instruction);
                    }
                }
            }
        }
//        result.add(".data");
//        for (Instruction _data : data) {
//            result.add(_data.toString());
//        }
//        for (String reg : regs) {
//            String dataLabel = reg + ": .word 0";
//            result.add(dataLabel);
//        }
        result.add(".text");
        result.add(".globl main");
        for (Instruction _text : text) {
            result.add(_text.toString());
        }
        return result;
    }

    public static void dumpMipsFile(String outFileName, List<String> code) throws IOException {
        Path filePath = Paths.get(outFileName);
        Files.write(filePath, code, Charset.forName("UTF-8"));
    }
}
