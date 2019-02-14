package org.ternlang.core;

public enum ModifierType {
   VARIABLE(0x00001, "var", "let"),
   CONSTANT(0x00002, "const"),
   FUNCTION(0x00004, "function", "func"),
   ALIAS(0x00008, "type"),
   CLASS(0x00010, "class"),
   TRAIT(0x00020, "trait"),
   ENUM(0x00040, "enum"),
   MODULE(0x00080, "module"),
   OVERRIDE(0x00100, "override"),
   PRIVATE(0x00200, "private"),
   PUBLIC(0x00400, "public"),
   PROTECTED(0x00800, "protected"), // java only
   STATIC(0x01000, "static"),
   ABSTRACT(0x02000, "abstract"),
   ASYNC(0x04000, "async"),
   PROXY(0x08000, "proxy"),
   CLOSURE(0x10000, "->"),
   ARRAY(0x20000, "[]"),
   VARARGS(0x40000, "..."),
   ANY(0x80000, "?");
   
   public final String[] tokens;
   public final int mask;
   
   private ModifierType(int mask, String... tokens) {
      this.tokens = tokens;
      this.mask = mask;
   }
   
   public static boolean isDefault(int modifier){
      return modifier == -1;
   }
   
   public static boolean isStatic(int modifier){
      return modifier >= 0 && (STATIC.mask & modifier) != 0;
   }
   
   public static boolean isModule(int modifier){
      return modifier >= 0 && (MODULE.mask & modifier) != 0;
   }

   public static boolean isAlias(int modifier){
      return modifier >= 0 && (ALIAS.mask & modifier) != 0;
   }

   public static boolean isClass(int modifier){
      return modifier >= 0 && (CLASS.mask & modifier) != 0;
   }
   
   public static boolean isTrait(int modifier){
      return modifier >= 0 && (TRAIT.mask & modifier) != 0;
   }
   
   public static boolean isEnum(int modifier){
      return modifier >= 0 && (ENUM.mask & modifier) != 0;
   }

   public static boolean isProxy(int modifier){
      return modifier >= 0 && (PROXY.mask & modifier) != 0;
   }
   
   public static boolean isFunction(int modifier){
      return modifier >= 0 && (FUNCTION.mask & modifier) != 0;
   }
   
   public static boolean isArray(int modifier){
      return modifier >= 0 && (ARRAY.mask & modifier) != 0;
   }
   
   public static boolean isConstant(int modifier){
      return modifier >= 0 && (CONSTANT.mask & modifier) != 0;
   }  
   
   public static boolean isVariable(int modifier){
      return modifier >= 0 && (VARIABLE.mask & modifier) != 0;
   }  
   
   public static boolean isOverride(int modifier) {
      return modifier >= 0 && (OVERRIDE.mask & modifier) != 0;
   }
   
   public static boolean isAbstract(int modifier) {
      return modifier >= 0 && (ABSTRACT.mask & modifier) != 0;
   }
   
   public static boolean isPublic(int modifier) {
      return modifier >= 0 && (PUBLIC.mask & modifier) != 0;
   }
   
   public static boolean isPrivate(int modifier) {
      return modifier >= 0 && (PRIVATE.mask & modifier) != 0;
   }
   
   public static boolean isProtected(int modifier) {
      return modifier >= 0 && (PROTECTED.mask & modifier) != 0;
   }
   
   public static boolean isVariableArgument(int modifier) {
      return modifier >= 0 && (VARARGS.mask & modifier) != 0;
   }

   public static boolean isClosure(int modifier) {
      return modifier >= 0 && (CLOSURE.mask & modifier) != 0;
   }

   public static boolean isAsync(int modifier) {
      return modifier >= 0 && (ASYNC.mask & modifier) != 0;
   }
   
   public static boolean isAny(int modifier) {
      return modifier >= 0 && (ANY.mask & modifier) != 0;
   }
   
   public static ModifierType resolveModifier(String token) {
      if(token != null) {
         for(ModifierType modifier : VALUES){
            for(int i = 0; i < modifier.tokens.length; i++) {
               if(modifier.tokens[i].equals(token)) {
                  return modifier;
               }
            }
         }
      }
      return null;
   }
   
   private static final ModifierType[] VALUES = {
      VARIABLE,
      CONSTANT,
      FUNCTION, 
      CLASS,
      TRAIT,
      ENUM,
      MODULE,
      OVERRIDE,
      PRIVATE,
      PUBLIC,
      PROTECTED,
      STATIC,
      ABSTRACT,
      ASYNC
   };
}