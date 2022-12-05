package org.ternlang.parse.insertion;

import org.ternlang.common.ArrayStack;
import org.ternlang.common.Stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class CompoundNode implements ScopeNode {

   private final Consumer<CharSequence> buffer;


   // searching history for scope type is probably best. i.e parent.isClass
   // doing a for(int i ....) if(segments.get(i).isClass())
   private final Deque<Segment> segments;
   private final Stack<CompoundNode> scopes;
   private final CompoundNode parent;
   private SegmentType context; // class, enum, trait, if, while, until
   private SegmentType pending; // class, enum, trait, if, while, until
   private CompoundNode current;

   public CompoundNode(Consumer<CharSequence> buffer) {
      this(buffer, null, SegmentType.OPEN_BLOCK);
   }

   public CompoundNode(Consumer<CharSequence> buffer, CompoundNode parent, SegmentType context) {
      this.segments = new ArrayDeque<>();
      this.scopes = new ArrayStack<>();
      this.context = context;
      this.buffer = buffer;
      this.parent = parent;
      this.current = null;
   }

   public void add(Segment segment) {
      if (current != null) {
         current.update(segment); // forward down
      } else {
         update(segment);
      }
   }

   @Override
   public void update(Segment segment) {
      SegmentType type = segment.type();

      switch (type) {
         case OPEN_EXPRESSION:
         case OPEN_ARRAY:
         case OPEN_BLOCK:
            open(segment);
         case CLOSE_EXPRESSION:
         case CLOSE_ARRAY:
         case CLOSE_BLOCK:
            close(segment);
         case TYPE:
         case BRANCH_CONDITION:
         case BRANCH_NO_CONDITION:
            context = type; // this means that when we open a block we may not need to close it
      }
      process(segment);
   }

   private void process(Segment segment) {
      SegmentType type = segment.type();

      if (type.isFloating()) {

      } else if (type.isSpace()) {
         buffer.accept(segment.source());
      } else if (type.isReturn()) {
         SegmentType previous = segments.isEmpty() ? SegmentType.NONE : segments.getFirst().type();

         if (previous.isFlowControl()) {
            buffer.accept(";");
         }
         buffer.accept(segment.source());
      } else {
         segments.addFirst(segment);
      }
   }

   private void open(Segment segment) {
      SegmentType type = segment.type();

      if (type.isOpenBlock()) {
         SegmentType previous = segments.isEmpty() ? SegmentType.NONE : segments.getFirst().type();

         switch(previous) {
            case TEXT:
            case OPERATOR:
            case FLOATING:
            case SYMBOL:
            case CLOSE_ARRAY:
            case CLOSE_EXPRESSION:
               if(context == null) {
                  buffer.accept(";");
               }
         }
         current = new CompoundNode(buffer);
         context = null; // no longer needed for condition
      }
   }

   private void close(Segment segment) {
      SegmentType type = segment.type();

      if (type.isCloseBlock()) {
         SegmentType previous = segments.isEmpty() ? SegmentType.NONE : segments.getFirst().type();

         switch(previous) {
            case TEXT:
            case OPERATOR:
            case FLOATING:
            case SYMBOL:
            case CLOSE_ARRAY:
            case CLOSE_EXPRESSION:
               buffer.accept(";");
         }
         if (parent != null) {
            parent.current = parent;
         }
      }
   }
}
