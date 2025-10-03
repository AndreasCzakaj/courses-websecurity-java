import {describe, expect} from "vitest";

function checkAndSanitize(input: string ): string {
  if (input === undefined || input === null) {
    throw new Error("input must be a value")
  }

  let out = input.trim()
  if (out.length < 1) {
    throw new Error("min 1 char needed")
  }

  return out
}

describe("Validation Test", () => {
  const shouldPassTestCases = [
    { input: "CJ",      expected: "CJ" },
    { input: "Joey ",   expected: "Joey" },
    //{ input: "<script", expected: "script" },
  ]

  test.each(shouldPassTestCases)(
    'should pass for input "$input", sanitized to "$expected"',
    ({ input, expected }) => {
      const actual = checkAndSanitize(input)
      expect(actual).toBe(expected)
    }
  )

  test.each`
    input        | expected
    ${""}        | ${"min 1 char needed"}
    ${undefined} | ${"input must be a value"}
    ${null}      | ${"input must be a value"}
`('should fail for input "$input" with message $expected', ({ input, expected }) => {
    const action = () => checkAndSanitize(input)
    expect(action).toThrow(new Error(expected)) // JestExtended

    // Java AssertJ:
    // Throwable action = () -> validation.checkAndSanitize(input))
    // assertThat(action)
    //     .isInstanceOf(ValidationException.class)
    //     .hasMessage(expected);
  })
})


type User = {
  firstName: string
}

function validateUser(user: User) {
  if (user.firstName.length < 2) {
    throw new Error("First name must be at least 2 characters long")
  }
}

describe("validation hack", () => {

  test("one OK", () => {
    const given: User = {
      firstName: "CJ"
    }

    const action = () => validateUser(given)

    expect(action).not.toThrow(Error)
  })

  test("one NOK", () => {
    const given: User = {
      firstName: "C"
    }

    const action = () => validateUser(given)

    expect(action).toThrow(new Error("First name must be at least 2 characters long"))
  })

  test("hack", () => {
    // in the real world, this could be JSON that someone sent to you
    const given: User = {
      firstName: {
        length: 2
      }
    }

    const action = () => validateUser(given)

    expect(action).not.toThrow(Error)
  })
})