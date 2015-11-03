package org.kefirsf.bb.proc;

/**
 * Check if the variable is equals with context value
 */
public class Check extends ProcNamedElement implements ProcPatternElement {
    private final boolean ghost;

    public Check(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        if(isNextIn(context)){
            if (!ghost) {
                context.getSource().incOffset(getContextLength(context));
            }
            return true;
        } else {
            return false;
        }
    }

    private CharSequence getContextValue(Context context) {
        return (CharSequence) context.getAttribute(getName());
    }

    private int getContextLength(Context context) {
        CharSequence value = getContextValue(context);
        if(value!=null){
            return value.length();
        }else{
            return 0;
        }
    }

    public boolean isNextIn(Context context) {
        Source source = context.getSource();
        int offset = source.getOffset();

        CharSequence old = getContextValue(context);
        if(old==null){
            return false;
        }

        int length = old.length();
        CharSequence val = source.sub(offset + length);
        return val.equals(old);
    }

    public int findIn(Source source) {
        return -1;
    }
}
