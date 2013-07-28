package com.buschmais.jqassistant.scanner.impl.visitor;

import com.buschmais.jqassistant.core.model.api.descriptor.AnnotatedDescriptor;
import com.buschmais.jqassistant.core.model.api.descriptor.ClassDescriptor;
import com.buschmais.jqassistant.core.model.api.descriptor.DependentDescriptor;
import com.buschmais.jqassistant.scanner.impl.resolver.DescriptorResolverFactory;
import com.buschmais.jqassistant.store.api.Store;
import org.objectweb.asm.Type;

public abstract class AbstractVisitor {

    private final DescriptorResolverFactory resolverFactory;

    protected AbstractVisitor(DescriptorResolverFactory resolverFactory) {
        this.resolverFactory = resolverFactory;
    }

    protected DescriptorResolverFactory getResolverFactory() {
        return resolverFactory;
    }

    protected Store getStore() {
        return resolverFactory.getStore();
    }

    protected ClassDescriptor getClassDescriptor(String typeName) {
        String fullQualifiedName = getType(Type.getObjectType(typeName));
        return resolverFactory.getClassDescriptorResolver().resolve(fullQualifiedName);
    }

    protected void addAnnotation(AnnotatedDescriptor annotatedDescriptor, String typeName) {
        if (typeName != null) {
            ClassDescriptor dependency = getClassDescriptor(typeName);
            annotatedDescriptor.getAnnotatedBy().add(dependency);
        }
    }

    protected void addDependency(DependentDescriptor dependentDescriptor, String typeName) {
        if (typeName != null) {
            ClassDescriptor dependency = getClassDescriptor(typeName);
            dependentDescriptor.getDependencies().add(dependency);
        }
    }

    protected String getType(final String desc) {
        return getType(Type.getType(desc));
    }

    protected String getType(final Type t) {
        switch (t.getSort()) {
            case Type.ARRAY:
                return getType(t.getElementType());
            default:
                return t.getClassName();
        }
    }

}
