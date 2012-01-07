package org.kefirsf.bb.conf;

import org.kefirsf.bb.AbstractCode;
import org.kefirsf.bb.Util;
import org.kefirsf.bb.WScope;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scope definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Scope {
    /**
     * Default name for root scope. If ROOT scope not defined in configuration
     * then all codes add to default ROOT scope.
     */
    public static final String ROOT = "ROOT";

    private final String name;
    private String parent;
    private boolean ignoreText;
    private Set<Code> codes = new HashSet<Code>();

    public Scope() {
        name = Util.generateRandomName();
    }

    public Scope(String name) {
        this.name = name;
        this.parent = null;
        this.ignoreText = false;
    }

    public Scope(String name, String parent, boolean ignoreText) {
        this.name = name;
        this.parent = parent;
        this.ignoreText = ignoreText;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public boolean isIgnoreText() {
        return ignoreText;
    }

    public Set<Code> getCodes() {
        return codes;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setIgnoreText(boolean ignoreText) {
        this.ignoreText = ignoreText;
    }

    public void setCodes(Set<Code> codes) {
        this.codes = codes;
    }

    public void addCode(Code code) {
        codes.add(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        return name.equals(scope.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Create scope
     *
     * @param configuration text processor configuration
     * @param createdScopes created scopes
     * @param codes         codes
     * @return scope
     */
    WScope create(Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes) {
        WScope created = createdScopes.get(this);
        if (created == null) {
            created = new WScope(getName());
            createdScopes.put(this, created);
            created.setIgnoreText(isIgnoreText());
            if (getParent() != null) {
                created.setParent(configuration.getScope(getParent()).create(configuration, createdScopes, codes));
            }
            Set<AbstractCode> scopeCodes = new HashSet<AbstractCode>();
            for (Code code : getCodes()) {
                scopeCodes.add(code.create(configuration, createdScopes, codes));
            }
            created.setScopeCodes(scopeCodes);
            created.init();
        }
        return created;
    }
}
