package eu.sarunas.atf.generators.model.dresden;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.modelinstancetype.exception.TypeNotFoundInModelException;
import tudresden.ocl20.pivot.modelinstancetype.java.JavaModelInstanceTypePlugin;
import tudresden.ocl20.pivot.modelinstancetype.java.internal.msg.JavaModelInstanceTypeMessages;
import tudresden.ocl20.pivot.modelinstancetype.java.internal.util.JavaModelInstanceTypeUtility;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceElement;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceEnumerationLiteral;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceFactory;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceObject;
import tudresden.ocl20.pivot.modelinstancetype.types.base.BasisJavaModelInstanceFactory;
import tudresden.ocl20.pivot.pivotmodel.Enumeration;
import tudresden.ocl20.pivot.pivotmodel.EnumerationLiteral;
import tudresden.ocl20.pivot.pivotmodel.Type;
import org.eclipse.osgi.util.NLS;

/**
 * <p>
 * A factory that is used to create {@link IModelInstanceElement} for Java {@link Object} s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class JavaModelInstanceFactory extends BasisJavaModelInstanceFactory implements
    IModelInstanceFactory {

  /** The {@link Logger} for this class. */
  private static final Logger LOGGER = JavaModelInstanceTypePlugin
      .getLogger(JavaModelInstanceFactory.class);

  /**
   * The already adapted {@link IModelInstanceElement}s of this {@link JavaModelInstanceFactory}.
   * <strong>This is a {@link WeakHashMap}! If an {@link Object} is disposed, its adapted
   * {@link IModelInstanceElement} will be disposed as well.</p>
   */
  private final Map<Object, IModelInstanceElement> myCachedAdaptedObjects =
      new WeakHashMap<Object, IModelInstanceElement>();

  /** The IModel for that {@link IModelInstanceElement} shall be created. */
  private final IModel myModel;

  /**
   * <p>
   * Creates a new {@link JavaModelInstanceFactory} for a given {@link IModel} .
   * </p>
   */
  public JavaModelInstanceFactory(IModel model) {

    this.myModel = model;
  }

  /*
   * (non-Javadoc)
   * 
   * @seetudresden.ocl20.pivot.modelbus.modelinstance.types.impl.java. BasisJavaModelInstanceFactory
   * #createModelInstanceElement(java.lang.Object)
   */
  @Override
  public IModelInstanceElement createModelInstanceElement(Object adapted)
      throws TypeNotFoundInModelException {

    /* Probably debug the entry of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createModelInstanceElement("; //$NON-NLS-1$
      msg += "adapted = " + adapted; //$NON-NLS-1$
      msg += ")"; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    IModelInstanceElement result;

    /* Check if the object has already been adapted. */
    result = this.myCachedAdaptedObjects.get(adapted);

    /* Else create a new adapter. */
    if (result == null) {

      /*
       * Try to use the BasisJavaModelInstanceFactory to probably create an adapter for a primitive
       * type or a collection.
       */
      result = super.createModelInstanceElement(adapted);

      /* Check if no primitive type or collection has been created. */
      if (result == null) {

        /* Check if the object is an EnumerationLiteral. */
        if (adapted.getClass().isEnum()) {
          result = this.createJavaModelInstanceEnumerationLiteral((Enum<?>) adapted);
        }

        /* Else create an IModelInstanceObject. */
        else {
          result = createJavaModelInstanceObject(adapted);
        }
        // no else.

        /* Cache the created object. */
        /* Only adapted arrays and normal objects are cached. */
        this.myCachedAdaptedObjects.put(adapted, result);
      }
      // no else.
    }
    // no else.

    /* Probably debug the exit of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createModelInstanceElement(Object) - exit"; //$NON-NLS-1$
      msg += " - rseult = " + result; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @seetudresden.ocl20.pivot.modelbus.modelinstance.types.base. BasisJavaModelInstanceFactory
   * #createModelInstanceElement(java.lang.Object, tudresden.ocl20.pivot.pivotmodel.Type)
   */
  @Override
  public IModelInstanceElement createModelInstanceElement(Object adapted, Type type) {

    /* Probably debug the entry of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createModelInstanceElement("; //$NON-NLS-1$
      msg += "adapted = " + adapted; //$NON-NLS-1$
      msg += ", type = " + type; //$NON-NLS-1$
      msg += ")"; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    IModelInstanceElement result;

    /* Check if the object has already been adapted. */
    result = this.myCachedAdaptedObjects.get(adapted);

    /* Else create a new adapter. */
    if (result == null) {

      /*
       * Try to use the BasisJavaModelInstanceFactory to probably create an adapter for a primitive
       * type or a collection.
       */
      result = super.createModelInstanceElement(adapted, type);

      /* Check if no primitive type or collection has been created. */
      if (result == null) {

        /* Check if the given type is an Enumeration. */
        if (type instanceof Enumeration) {

          /*
           * If adapted == null, i.e. a PropertyCallExp or OperationCallExp returned a null value,
           * simply try to create an undefined value.
           */
          if (adapted == null) {
            result = createEnumerationLiteral(adapted, type);
          }

          else {
            /*
             * Check if the object is an EnumerationLiteral and has the same type as the given type.
             */
            if (adapted.getClass().isEnum()
                && JavaModelInstanceTypeUtility.toQualifiedNameList(
                    adapted.getClass().getCanonicalName()).equals(type.getQualifiedNameList())) {
              result = createEnumerationLiteral(adapted, type);
            }
          }

          /* Else throw an exception. */
          if (result == null) {
            String msg;

            msg = JavaModelInstanceTypeMessages.JavaModelInstance_CannotAdaptToType;
            msg = NLS.bind(msg, adapted, type);

            throw new IllegalArgumentException();
          }
          // no else.
        }

        /* Else create an IModelInstanceObject. */
        else {

          /* Try to find a type for the given object. */
          try {
            if (adapted != null) {

              Type ownType;
              ownType = this.findTypesOfObjectInModel(adapted);

              /*
               * If the found type conforms to the given type, adapt it.
               */
              if (ownType.conformsTo(type)) {
                result = createJavaModelInstanceObject(adapted, ownType);
              }

              else {
                /*
                 * FIXME Claas: Is this okay? Shouldn't an Exception be thrown instead. Else use the
                 * given type.
                 */
                result = createJavaModelInstanceObject(adapted, type);
              }
              // end else.
            }

            /* Result is undefined. Use the type of the property. */
            else {
              result = createJavaModelInstanceObject(adapted, type);
            }
          }

          catch (TypeNotFoundInModelException e) {
            /*
             * FIXME Claas: Is this okay? Shouldn't an Exception be thrown instead. Else use the
             * given type.
             */
            result = createJavaModelInstanceObject(adapted, type);
          }
        }
        // no else.

        /* Cache the created object. */
        /* Only adapted arrays and normal objects are cached. */
        this.myCachedAdaptedObjects.put(adapted, result);
      }
      // no else.
    }
    // no else.

    /* Probably debug the exit of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createModelInstanceElement(Object) - exit"; //$NON-NLS-1$
      msg += " - rseult = " + result; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    return result;
  }

  private IModelInstanceElement createEnumerationLiteral(Object adapted, Type type) {

    IModelInstanceElement result;

    /*
     * Since an enumeration literal could not be found in the model, handle this case.
     */
    if (adapted == null) {
      result = BasisJavaModelInstanceFactory.createModelInstanceEnumerationLiteral(null);
    }

    else {
      try {
        result = this.createJavaModelInstanceEnumerationLiteral((Enum<?>) adapted);
      }

      catch (TypeNotFoundInModelException e) {
        String msg;

        msg = JavaModelInstanceTypeMessages.JavaModelInstance_CannotAdaptToType;
        msg = NLS.bind(msg, adapted, type);

        throw new IllegalArgumentException();
      }
    }

    return result;
  }

  /**
   * <p>
   * Creates an {@link IModelInstanceEnumerationLiteral} for a given {@link Enum} object.
   * </p>
   * 
   * @param anEnum The {@link Enum} that shall be adapted.
   * @return The adapted {@link IModelInstanceEnumerationLiteral}.
   * @throws TypeNotFoundInModelException Thrown, if a given {@link Enum} cannot be adapted to a
   *         {@link Type} in the {@link IModel}.
   */
  private IModelInstanceElement createJavaModelInstanceEnumerationLiteral(Enum<?> anEnum)
      throws TypeNotFoundInModelException {

    /* Probably debug the entry of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createJavaModelInstanceEnumerationLiteral("; //$NON-NLS-1$
      msg += "literal = " + anEnum; //$NON-NLS-1$
      msg += ")"; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    IModelInstanceEnumerationLiteral result;
    Type type;

    result = null;

    /* Try to find the enumeration of the enum in the model. */
    type = this.findTypeOfClassInModel(anEnum.getClass());

    /* Check if the enumeration has been found in the model. */
    if (type != null && type instanceof Enumeration) {

      Enumeration enumeration;
      enumeration = (Enumeration) type;

      /* Try to find a literal with the right value. */
      for (EnumerationLiteral aLiteral : enumeration.getOwnedLiteral()) {

        if (aLiteral.getName().equals(anEnum.toString())) {

          result = createModelInstanceEnumerationLiteral(aLiteral);
          break;
        }
        // no else.
      }
      // end for.

      if (result == null) {
        String msg;

        msg = JavaModelInstanceTypeMessages.JavaModelInstance_TypeNotFoundInModel;
        msg = NLS.bind(msg, anEnum);

        throw new TypeNotFoundInModelException(msg);
      }
    }

    else {
      String msg;

      msg = JavaModelInstanceTypeMessages.JavaModelInstance_TypeNotFoundInModel;
      msg = NLS.bind(msg, anEnum);

      throw new TypeNotFoundInModelException(msg);
    }

    /* Probably debug the exit of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createJavaModelInstanceEnumerationLiteral(Enum<?>) - exit"; //$NON-NLS-1$
      msg += " - reult = " + result; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    return result;
  }

  /**
   * <p>
   * Creates an {@link IModelInstanceObject} for a given {@link Object}.
   * </p>
   * 
   * @param object The {@link Object} that shall be adapted.
   * @return The create {@link IModelInstanceObject}.
   * @throws TypeNotFoundInModelException Thrown, if a given {@link Object} cannot be adapted to a
   *         {@link Type} in the {@link IModel}.
   */
  private IModelInstanceObject createJavaModelInstanceObject(Object object)
      throws TypeNotFoundInModelException {

    /* Probably debug the entry of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createNormalObject("; //$NON-NLS-1$
      msg += "object = " + object; //$NON-NLS-1$
      msg += ")"; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    IModelInstanceObject result;

    Type modelType;
    modelType = this.findTypesOfObjectInModel(object);

    result = new WrapperModelInstanceObject(object, modelType, modelType, this);

    /* Probably debug the exit of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createNormalObject(Object) - exit"; //$NON-NLS-1$
      msg += " - reult = " + result; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    return result;
  }

  /**
   * <p>
   * Creates an {@link IModelInstanceObject} for a given {@link Object} and a given {@link Type}.
   * </p>
   * 
   * @param object The {@link Object} that shall be adapted.
   * @param The {@link Type} the created {@link IModelInstanceObject} shall have. <strong>Please
   *        note, that the adapted element could also have a sub-{@link Type} of the given
   *        {@link Type}!</strong>
   * @return The create {@link IModelInstanceObject}.
   */
  private IModelInstanceObject createJavaModelInstanceObject(Object object, Type type) {

    /* Probably debug the entry of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createNormalObject("; //$NON-NLS-1$
      msg += "object = " + object; //$NON-NLS-1$
      msg += ", type = " + type; //$NON-NLS-1$
      msg += ")"; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    IModelInstanceObject result;

    /*
     * In case of an OperationCallExp or PropertyCallExp, a queried model element can return null.
     * Handle this case here.
     */
    if (object == null) {
      result = new WrapperModelInstanceObject(null, type, type, this);
    } else {
      Class<?> typeClass;
      String canonicalName;

      /* Convert the type's name into a canonical name. */
      canonicalName = JavaModelInstanceTypeUtility.toCanonicalName(type.getQualifiedNameList());

      /* Try to find the type's class. */
      try {
        // typeClass = object.getClass().getClassLoader().loadClass(canonicalName);
        typeClass = getClass(object, canonicalName);

        /* Check if the given object conforms to the found class. */
        if (typeClass.isAssignableFrom(object.getClass())) {
          Type originalType;
          try {
            originalType = this.findTypeOfClassInModel(object.getClass());

            result = new WrapperModelInstanceObject(object, typeClass, type, originalType, this);
          }

          catch (TypeNotFoundInModelException e) {
            /* Create an undefined instance object. */
            result = new WrapperModelInstanceObject(null, typeClass, type, type, this);
          }
        }

        else {
          /* Create an undefined instance object. */
          result = new WrapperModelInstanceObject(null, typeClass, type, type, this);
        }
      }

      catch (ClassNotFoundException e) {
        /* Create an undefined instance object. */
        result = new WrapperModelInstanceObject(null, type, type, this);
      }
    }

    /* Probably debug the exit of this method. */
    if (LOGGER.isDebugEnabled()) {
      String msg;

      msg = "createNormalObject(Object, Type) - exit"; //$NON-NLS-1$
      msg += " - reult = " + result; //$NON-NLS-1$

      LOGGER.debug(msg);
    }
    // no else.

    return result;
  }

  /**
   * <p>
   * Returns the class object of the given class name. It takes care of primitive classes and is
   * able to construct these.
   * </p>
   * 
   * @param object the object whose class loader should be used
   * @param canonicalName the canonical name of the class to load
   * @return the class object of the given name
   * @throws ClassNotFoundException if the class could not be found
   * @author Lars Schuetze
   * @since 3.3.0
   */
  private Class<?> getClass(Object object, String canonicalName) throws ClassNotFoundException {
    if ("int".equals(canonicalName)) {
      return int.class;
    }
    if ("float".equals(canonicalName)) {
      return float.class;
    }
    if ("double".equals(canonicalName)) {
      return double.class;
    }
    if ("long".equals(canonicalName)) {
      return long.class;
    }
    if ("java.util.String".equals(canonicalName)) {
      return String.class;
    }
    ClassLoader cl = object.getClass().getClassLoader();
    if (cl != null) {
      return cl.loadClass(canonicalName);
    }
    return Class.forName(canonicalName);
  }

  /**
   * <p>
   * A helper method that returns the {@link Type} in a given {@link IModel} that correspond to a
   * given {@link Class}.
   * </p>
   * 
   * @param aClass The {@link Class} for that the {@link Type} shall be returned.
   * @return The found {@link Type}.
   * @throws TypeNotFoundInModelException Thrown, if a given {@link Object} cannot be adapted to a
   *         {@link Type} in the {@link IModel}.
   */
  private Type findTypeOfClassInModel(Class<?> aClass) throws TypeNotFoundInModelException {

    Type result;

    try {
      result =
          this.myModel.findType(JavaModelInstanceTypeUtility.toQualifiedNameList(aClass
              .getCanonicalName()));
    }

    catch (ModelAccessException e) {
      result = null;
    }

    /* Probably throw an exception. */
    if (result == null) {
      String msg;

      msg = JavaModelInstanceTypeMessages.JavaModelInstance_TypeNotFoundInModel;
      msg = NLS.bind(msg, aClass);

      throw new TypeNotFoundInModelException(msg);
    }
    // no else.

    return result;
  }

  /**
   * <p>
   * A helper method that tries to find a {@link Type} in the {@link IModel} that corresponds to the
   * given class.
   * </p>
   * 
   * <p>
   * Probably a {@link Class} could be related to different {@link Type}s in the {@link IModel} that
   * do not know each other. E.g., multiple interface inheritance. In these cases, the result will
   * be a {@link Set} containing all {@link Type}s, the {@link Class} could be related to.
   * </p>
   * 
   * <p>
   * The search strategy will work as follows:
   * </p>
   * <ul>
   * <li>If the {@link Class} itself is represented by a {@link Type} in the {@link IModel}, only
   * this {@link Type} will be returned.</li>
   * <li>Else the method will collect all {@link Type}s that are related to implemented interfaces
   * of the {@link Class} and probably also the {@link Type} of its super {@link Class}.</li>
   * </ul>
   * 
   * @param aClass The {@link Class} for that the {@link Type}s shall be returned.
   * @return The found {@link Type}s as an array.
   * @throws TypeNotFoundInModelException Thrown, if a given {@link Object} cannot be adapted to a
   *         {@link Type} in the {@link IModel}.
   */
  private Set<Type> findTypesOfClassInModel(Class<?> clazz) throws TypeNotFoundInModelException {

    Set<Type> result;

    result = new HashSet<Type>();

    /* Check that the given class is not null. */
    if (clazz != null) {

      /* Try to find the type corresponding to the class itself. */
      try {
        result.add(this.findTypeOfClassInModel(clazz));
      }

      /*
       * Else search for the interfaces in the model and for the super type.
       */
      catch (TypeNotFoundInModelException e) {

        /* Add the types of the implemented interfaces. */
        for (Class<?> anInterface : clazz.getInterfaces()) {
          try {
            result.addAll(findTypesOfClassInModel(anInterface));
          }

          catch (TypeNotFoundInModelException e2) {
            /* Continue probably the class will implement a type. */
          }
        }

        /* Add recursively found types for the super class. */
        try {
          result.addAll(findTypesOfClassInModel(clazz.getSuperclass()));
        }

        catch (TypeNotFoundInModelException e2) {
          /*
           * Continue probably one of the interfaces will implement a type.
           */
        }

        /*
         * Remove types, that are already represented by sub types in the model.
         */
        result = this.removeRedundantModelTypes(result);
      }
      // end else.
    }

    /*
     * Check if any implemented type has been found. Else throw an exception.
     */
    if (result.size() == 0) {
      String msg;

      msg = JavaModelInstanceTypeMessages.JavaModelInstance_TypeNotFoundInModel;
      msg = NLS.bind(msg, clazz);

      throw new TypeNotFoundInModelException(msg);
    }

    return result;
  }

  /**
   * <p>
   * A helper method that searches for a given Java {@link Object} the corresponding {@link Type}s
   * in the {@link IModel}.
   * </p>
   * 
   * @param object The {@link Object} for which the {@link Type} shall be returned.
   * @return The found {@link Type}.
   * @throws TypeNotFoundInModelException Thrown, if a given {@link Object} cannot be adapted to a
   *         {@link Type} in the {@link IModel}.
   */
  private Type findTypesOfObjectInModel(Object object) throws TypeNotFoundInModelException {

    Type result;
    Set<Type> resultSet;

    Class<?> objectClass;
    objectClass = object.getClass();

    resultSet = findTypesOfClassInModel(objectClass);

    if (resultSet.size() == 1) {
      result = resultSet.iterator().next();
    }

    else {
      result = super.createComplexType(resultSet);
    }

    return result;
  }

  /**
   * <p>
   * A helper method that removes {@link Type}s from a given {@link Set} that are transitively
   * described by other {@link Type}s of the {@link Set} because they are super {@link Type}s.
   * </p>
   * 
   * @param types The {@link Set} from which super {@link Type}s shall be removed.
   * @return The {@link Set} without redundant super {@link Type}s.
   */
  private Set<Type> removeRedundantModelTypes(Set<Type> types) {

    List<Type> typeList;
    Set<Type> result;

    typeList = new ArrayList<Type>(types);
    result = new HashSet<Type>();

    for (int index1 = 0; index1 < typeList.size(); index1++) {

      Type type1;
      boolean isRedundant;

      type1 = typeList.get(index1);
      isRedundant = false;

      /* Check if any other type is a sub type of type 1. */
      for (int index2 = 0; index2 < typeList.size(); index2++) {

        Type type2;
        type2 = typeList.get(index2);

        if (index1 != index2 && type2.conformsTo(type1)) {
          isRedundant = true;
          break;
        }
        // no else.
      }

      if (!isRedundant) {
        result.add(type1);
      }
      // no else.
    }

    return result;
  }
}
