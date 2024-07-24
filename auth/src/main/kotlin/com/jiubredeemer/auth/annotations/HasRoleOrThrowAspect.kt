package com.jiubredeemer.auth.annotations

import com.jiubredeemer.auth.service.AccessChecker
import com.jiubredeemer.dal.entities.User
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class HasRoleOrThrowAspect @Autowired constructor(
    private val accessChecker: AccessChecker
) {

    @Before("@annotation(com.jiubredeemer.auth.annotations.HasRoleOrThrow)")
    fun checkRoles(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val annotation = method.getAnnotation(HasRoleOrThrow::class.java)
        val roles = annotation.value.map { User.Role.valueOf(it) }
        accessChecker.hasAnyRoleOrThrow(roles)
    }
}
