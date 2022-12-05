package org.ternlang.parse.insertion;

public enum ScopeNodeType {
   TYPE, // all opening scopes are functions
   FUNCTION, // current scope is a function
   NORMAL // default rules
}
