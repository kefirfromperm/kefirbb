package org.kefirsf.bb;

import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.proc.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class PatternElementFactory {
    private static final ProcBol PROC_BOL = new ProcBol();
    private static final PatternJunk JUNK = new PatternJunk();
    private static final ProcEol EOL = new ProcEol(false);
    private static final ProcEol EOL_GHOST = new ProcEol(true);
    private static final ProcBlankLine BLANK_LINE = new ProcBlankLine(false);
    private static final ProcBlankLine BLANK_LINE_GHOST = new ProcBlankLine(true);

    private final ProcessorBuilder processorBuilder;

    private Map<Constant, PatternConstant> constants;

    PatternElementFactory(ProcessorBuilder processorBuilder) {
        this.processorBuilder = processorBuilder;
    }

    ProcPatternElement create(PatternElement element) {
        if (element instanceof Variable) {
            return create((Variable) element);
        } else if (element instanceof Text) {
            return create((Text) element);
        } else if (element instanceof Constant) {
            return create((Constant) element);
        } else if (element instanceof Junk) {
            return JUNK;
        } else if (element instanceof Eol) {
            return ((Eol) element).isGhost() ? EOL_GHOST : EOL;
        } else if (element instanceof Bol) {
            return PROC_BOL;
        } else if (element instanceof BlankLine) {
            return ((BlankLine) element).isGhost() ? BLANK_LINE_GHOST : BLANK_LINE;
        } else if (element instanceof Url) {
            Url url = (Url) element;
            return new ProcUrl(url.getName(), url.isGhost(), url.isLocal(), url.isSchemaless());
        } else if (element instanceof Email){
            Email email = (Email) element;
            return new ProcEmail(email.getName(), email.isGhost());
        } else {
            throw new TextProcessorFactoryException("Unknown pattern element " + element.getClass().getName() + ".");
        }
    }

    private ProcPatternElement create(Variable variable) {
        if (variable.getAction() != Action.check) {
            return new ProcVariable(
                    variable.getName(),
                    variable.getRegex(),
                    variable.isGhost(),
                    variable.getAction());
        } else {
            return new Check(
                    variable.getName(),
                    variable.isGhost()
            );
        }
    }

    /**
     * Create a constant element for text parsing
     *
     * @param constant constant definition
     * @return pattern element for constant
     */
    private PatternConstant create(Constant constant) {
        if (!constants.containsKey(constant)) {
            constants.put(
                    constant,
                    new PatternConstant(constant.getValue(), constant.isIgnoreCase(), constant.isGhost())
            );
        }
        return constants.get(constant);
    }

    /**
     * @param text element definition
     * @return pattern element for text
     */
    private ProcPatternElement create(Text text) {
        Scope scope = text.getScope();
        if (scope != null) {
            return new ProcText(
                    text.getName(),
                    processorBuilder.createScope(scope),
                    text.isTransparent()
            );
        } else {
            return new ProcText(text.getName(), text.isTransparent());
        }
    }

    HashSet<PatternConstant> getConstants() {
        return new HashSet<PatternConstant>(constants.values());
    }

    void cleanConstants() {
        this.constants = new HashMap<Constant, PatternConstant>();
    }
}