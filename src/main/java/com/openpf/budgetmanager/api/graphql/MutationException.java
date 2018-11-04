package com.openpf.budgetmanager.api.graphql;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.language.SourceLocation;

import java.util.*;


public class MutationException extends GraphQLException implements GraphQLError {

    public MutationException(String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ExecutionAborted;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        /*
        This is needed to prevent putting full stack trace to a GraphQL response
        and not implement custom GraphQLErrorHandler bean.
         */
        return null;
    }
}
