package tern.tree.resume;

import static tern.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.scope.index.Local;
import tern.core.scope.index.ScopeIndex;
import tern.core.scope.index.ScopeTable;
import tern.core.variable.Value;
import tern.tree.DeclarationAllocator;
import tern.tree.Modifier;
import tern.tree.ModifierList;
import tern.tree.NameReference;
import tern.tree.literal.TextLiteral;

public class AwaitDeclaration extends Evaluation {

   private final AtomicReference<Address> location;
   private final DeclarationAllocator allocator;
   private final NameReference reference;
   private final ModifierList list;

   public AwaitDeclaration(Modifier modifier, TextLiteral identifier) {
      this(modifier, identifier, null);
   }

   public AwaitDeclaration(Modifier modifier, TextLiteral identifier, Constraint constraint) {
      this.allocator = new AwaitVariableAllocator(constraint);
      this.reference = new NameReference(identifier);
      this.location = new AtomicReference<Address>();
      this.list = new ModifierList(modifier);
   }

   @Override
   public void define(Scope scope) throws Exception {
      String name = reference.getName(scope);
      ScopeIndex index = scope.getIndex();
      Address address = index.index(name);

      location.set(address);
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      int modifiers = list.getModifiers();
      String name = reference.getName(scope);
      Local local = allocator.compile(scope, name, modifiers);
      ScopeTable table = scope.getTable();
      Address address = location.get();

      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return NONE;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      int modifiers = list.getModifiers();
      String name = reference.getName(scope);
      Local local = allocator.allocate(scope, name, modifiers);
      ScopeTable table = scope.getTable();
      Address address = location.get();

      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return local;
   }
}
