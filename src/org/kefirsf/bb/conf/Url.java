package org.kefirsf.bb.conf;

/**
 * The URL pattern element.
 *
 * @author kefir
 */
public class Url extends GhostableNamedElement {
    public static final String DEFAULT_NAME = "url";
    public static final boolean DEFAULT_LOCAL = false;
    public static final boolean DEFAULT_SCHEMALESS = false;

    /**
     * Accept local URLs.
     */
    private boolean local = DEFAULT_LOCAL;

    /**
     * Accept URLs without a schema. This URL can't be terminator and can't accept URLs with a schema.
     */
    private boolean schemaless = DEFAULT_SCHEMALESS;

    /**
     * Create an URL pattern element.
     *
     * @param name  variable name
     * @param ghost is it ghost?
     * @param local supports local URLs
     * @param schemaless true if accept URLs only without a schema, false otherwise
     */
    public Url(String name, boolean ghost, boolean local, boolean schemaless) {
        super(name, ghost);
        this.local = local;
        this.schemaless = schemaless;
    }

    /**
     * Local URLs a URLs without a schema and host
     *
     * @return true - if the URL can accept local URLs also,
     * false - if URL must be absolute.
     */
    public boolean isLocal() {
        return local;
    }

    /**
     * Set support local URLs.
     *
     * true - if the URL can accept local URLs also,
     * false - if URL must be absolute.
     *
     * @param local new value
     */
    public void setLocal(boolean local) {
        this.local = local;
    }

    /**
     * Is it URL without a schema? Like www.example.com
     *
     * @return true if accept URLs only without a schema, false otherwise
     */
    public boolean isSchemaless() {
        return schemaless;
    }

    /**
     * Set this is URL with schema or not?
     *
     * @param schemaless if true then without schema.
     */
    public void setSchemaless(boolean schemaless) {
        this.schemaless = schemaless;
    }
}
