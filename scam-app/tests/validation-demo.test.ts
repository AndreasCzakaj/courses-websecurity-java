import {describe, expect} from "vitest";

type User = {
  firstName: string
}

function validateUser(user: User) {
  if (user.firstName.length < 2) {
    throw new Error("First name must be at least 2 characters long")
  }
}

describe("validation demo", () => {

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